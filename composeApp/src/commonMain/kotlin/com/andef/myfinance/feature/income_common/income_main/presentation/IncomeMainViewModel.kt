package com.andef.myfinance.feature.income_common.income_main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.core.domain.income_common.income.usecases.DeleteIncomeUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.GetIncomesByDateRangeFlowUseCase
import com.andef.myfinance.feature.income_common.income_main.domain.entities.IncomeForLazyColumn
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

class IncomeMainViewModel(
    private val getIncomesByDateRangeFlowUseCase: GetIncomesByDateRangeFlowUseCase,
    private val deleteIncomeUseCase: DeleteIncomeUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(IncomeMainState())
    val state: StateFlow<IncomeMainState> = _state

    fun send(intent: IncomeMainIntent) {
        when (intent) {
            is IncomeMainIntent.BottomSheetVisibleChange -> {
                _state.value = _state.value.copy(
                    showBottomSheet = intent.isVisible,
                    idInBottomSheet = intent.id,
                    categoryInBottomSheet = intent.category,
                    dateInBottomSheet = intent.date,
                    amountInBottomSheet = intent.amount
                )
            }

            is IncomeMainIntent.ChangeDeleteDialogVisible -> {
                _state.value = _state.value.copy(deleteDialogVisible = intent.isVisible)
            }

            is IncomeMainIntent.DeleteIncome -> {
                deleteIncome(intent.id, intent.onError)
            }

            is IncomeMainIntent.SubscribeForIncomes -> {
                subscribeForIncomes(intent.startDate, intent.endDate)
            }

            is IncomeMainIntent.SaveScrollState -> {
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
    private fun subscribeForIncomes(startDate: LocalDate, endDate: LocalDate) {
        val lastDatesEqualsNull = lastStartDate == null && lastEndDate == null
        val lastAndCurDatesNotEquals = lastStartDate != startDate || lastEndDate != endDate
        val isError = _state.value.isError
        if (lastDatesEqualsNull || lastAndCurDatesNotEquals || isError) {
            lastStartDate = startDate
            lastEndDate = endDate
            job?.cancel()
            job = viewModelScope.launch {
                getIncomesByDateRangeFlowUseCase.invoke(startDate, endDate)
                    .onStart {
                        withContext(Dispatchers.Main) {
                            _state.value = _state.value.copy(isLoading = true, isError = false)
                        }
                    }
                    .map { incomes ->
                        val incomesForLazyColumn = incomes
                            .groupBy { income -> income.date }
                            .toList()
                            .sortedByDescending { it.first }
                            .map { (date, items) ->
                                val sortedItems = items.sortedByDescending { it.id }
                                val totalAmount = sortedItems.sumOf { it.amount }
                                IncomeForLazyColumn(date, totalAmount, sortedItems)
                            }
                        val totalAmount = incomes.sumOf { it.amount }
                        incomesForLazyColumn to totalAmount
                    }
                    .retry()
                    .catch {
                        withContext(Dispatchers.Main) {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                isError = true,
                                incomesForLazyColumn = emptyList(),
                                totalAmount = 0.0
                            )
                        }
                    }
                    .collect { mapPair ->
                        withContext(Dispatchers.Main) {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                incomesForLazyColumn = mapPair.first,
                                totalAmount = mapPair.second,
                                isError = false
                            )
                        }
                    }
            }
        }
    }

    private fun deleteIncome(id: Long, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                withContext(Dispatchers.IO) { deleteIncomeUseCase.invoke(id) }
            } catch (_: Exception) {
                onError("Ошибка! Попробуйте ещё раз!")
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}