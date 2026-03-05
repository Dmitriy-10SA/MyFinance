package com.andef.myfinance.feature.expense_common.expense_main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.core.domain.expense_common.expense.usecases.DeleteExpenseUseCase
import com.andef.myfinance.core.domain.expense_common.expense.usecases.GetExpensesByDateRangeFlowUseCase
import com.andef.myfinance.feature.expense_common.expense_main.domain.entities.ExpenseForLazyColumn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate

class ExpenseMainViewModel(
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val getExpensesByDateRangeFlowUseCase: GetExpensesByDateRangeFlowUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ExpenseMainState())
    val state: StateFlow<ExpenseMainState> = _state

    fun send(intent: ExpenseMainIntent) {
        when (intent) {
            is ExpenseMainIntent.BottomSheetVisibleChange -> {
                _state.value = _state.value.copy(
                    showBottomSheet = intent.isVisible,
                    idInBottomSheet = intent.id,
                    categoryInBottomSheet = intent.category,
                    dateInBottomSheet = intent.date,
                    amountInBottomSheet = intent.amount
                )
            }

            is ExpenseMainIntent.ChangeDeleteDialogVisible -> {
                _state.value = _state.value.copy(deleteDialogVisible = intent.isVisible)
            }

            is ExpenseMainIntent.DeleteExpense -> {
                deleteExpense(intent.id, intent.onError)
            }

            is ExpenseMainIntent.SubscribeForExpenses -> {
                subscribeForExpenses(intent.startDate, intent.endDate)
            }

            is ExpenseMainIntent.SaveScrollState -> {
                _state.value = _state.value.copy(
                    initialFirstVisibleItemIndex = intent.initialFirstVisibleItemIndex,
                    initialFirstVisibleItemScrollOffset = intent.initialFirstVisibleItemScrollOffset
                )
            }
        }
    }

    private var lastStartDate: LocalDate? = null
    private var lastEndDate: LocalDate? = null
    private var job: Job? = null
    private fun subscribeForExpenses(startDate: LocalDate, endDate: LocalDate) {
        val lastDatesEqualsNull = lastStartDate == null && lastEndDate == null
        val lastAndCurDatesNotEquals = lastStartDate != startDate || lastEndDate != endDate
        val isError = _state.value.isError
        if (lastDatesEqualsNull || lastAndCurDatesNotEquals || isError) {
            lastStartDate = startDate
            lastEndDate = endDate
            job?.cancel()
            job = viewModelScope.launch {
                getExpensesByDateRangeFlowUseCase.invoke(startDate, endDate)
                    .onStart {
                        withContext(Dispatchers.Main) {
                            _state.value = _state.value.copy(isLoading = true, isError = false)
                        }
                    }
                    .map { expenses ->
                        val expensesForLazyColumn = withContext(Dispatchers.IO) {
                            expenses
                                .groupBy { expense -> expense.date }
                                .toList()
                                .sortedByDescending { it.first }
                                .map { (date, items) ->
                                    val sortedItems = items.sortedByDescending { it.id }
                                    val totalAmount = sortedItems.sumOf { it.amount }
                                    ExpenseForLazyColumn(date, totalAmount, sortedItems)
                                }
                        }
                        val totalAmount = withContext(Dispatchers.IO) {
                            expenses.sumOf { it.amount }
                        }
                        expensesForLazyColumn to totalAmount
                    }
                    .retry()
                    .catch {
                        withContext(Dispatchers.Main) {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                isError = true,
                                expensesForLazyColumn = emptyList(),
                                totalAmount = 0.0
                            )
                        }
                    }
                    .collect { mapPair ->
                        withContext(Dispatchers.Main) {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                expensesForLazyColumn = mapPair.first,
                                totalAmount = mapPair.second,
                                isError = false
                            )
                        }
                    }
            }
        }
    }

    private fun deleteExpense(id: Long, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                withContext(Dispatchers.IO) { deleteExpenseUseCase.invoke(id) }
            } catch (_: Exception) {
                onError("Ошибка! Попробуйте ещё раз!")
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}