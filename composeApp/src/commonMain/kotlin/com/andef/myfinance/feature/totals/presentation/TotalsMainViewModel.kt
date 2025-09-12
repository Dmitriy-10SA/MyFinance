package com.andef.myfinance.feature.totals.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.core.domain.expense_common.expense.usecases.GetExpensesByDateRangeFlowUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.GetIncomesByDateRangeFlowUseCase
import kotlinx.coroutines.Dispatchers
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

class TotalsMainViewModel(
    private val getIncomesByDateRangeFlowUseCase: GetIncomesByDateRangeFlowUseCase,
    private val getExpensesByDateRangeFlowUseCase: GetExpensesByDateRangeFlowUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TotalsMainState())
    val state: StateFlow<TotalsMainState> = _state

    fun send(intent: TotalsMainIntent) {
        when (intent) {
            is TotalsMainIntent.SubscribeForAllIncomesAndExpenses -> {
                subscribeForIncomes(intent.startDate, intent.endDate)
                subscribeForExpenses(intent.startDate, intent.endDate)
            }
        }
    }

    private var lastExpensesStartDate: LocalDate? = null
    private var lastExpensesEndDate: LocalDate? = null
    private var expenseJob: Job? = null
    private fun subscribeForExpenses(startDate: LocalDate, endDate: LocalDate) {
        val lastDatesEqualsNull = lastExpensesStartDate == null && lastExpensesEndDate == null
        val lastAndCurDatesNotEquals =
            lastExpensesStartDate != startDate || lastExpensesEndDate != endDate
        val isError = _state.value.isError
        if (lastDatesEqualsNull || lastAndCurDatesNotEquals || isError) {
            lastExpensesStartDate = startDate
            lastExpensesEndDate = endDate
            expenseJob?.cancel()
            expenseJob = viewModelScope.launch {
                getExpensesByDateRangeFlowUseCase.invoke(startDate, endDate)
                    .onStart {
                        withContext(Dispatchers.Main) {
                            _state.value = _state.value.copy(isLoading = true, isError = false)
                        }
                    }
                    .map { expenses -> expenses.sumOf { it.amount } }
                    .retry()
                    .catch {
                        withContext(Dispatchers.Main) {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                isError = true,
                                totalExpensesAmount = 0.0
                            )
                        }
                    }
                    .collect { totalAmount ->
                        withContext(Dispatchers.Main) {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                totalExpensesAmount = totalAmount,
                                isError = false
                            )
                        }
                    }
            }
        }
    }

    private var lastIncomesStartDate: LocalDate? = null
    private var lastIncomesEndDate: LocalDate? = null
    private var incomesJob: Job? = null
    private fun subscribeForIncomes(startDate: LocalDate, endDate: LocalDate) {
        val lastDatesEqualsNull = lastIncomesStartDate == null && lastIncomesEndDate == null
        val lastAndCurDatesNotEquals =
            lastIncomesStartDate != startDate || lastIncomesEndDate != endDate
        val isError = _state.value.isError
        if (lastDatesEqualsNull || lastAndCurDatesNotEquals || isError) {
            lastIncomesStartDate = startDate
            lastIncomesEndDate = endDate
            incomesJob?.cancel()
            incomesJob = viewModelScope.launch {
                getIncomesByDateRangeFlowUseCase.invoke(startDate, endDate)
                    .onStart {
                        withContext(Dispatchers.Main) {
                            _state.value = _state.value.copy(isLoading = true, isError = false)
                        }
                    }
                    .map { incomes -> incomes.sumOf { it.amount } }
                    .retry()
                    .catch {
                        withContext(Dispatchers.Main) {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                isError = true,
                                totalIncomesAmount = 0.0
                            )
                        }
                    }
                    .collect { totalAmount ->
                        withContext(Dispatchers.Main) {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                totalIncomesAmount = totalAmount,
                                isError = false
                            )
                        }
                    }
            }
        }
    }
}