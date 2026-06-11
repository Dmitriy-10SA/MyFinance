package com.andef.myfinance.feature.expense_common.expense_analysis.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.core.domain.expense_common.expense.usecases.GetExpensesByDateRangeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate

class ExpenseAnalysisViewModel(
    private val getExpensesByDateRangeUseCase: GetExpensesByDateRangeUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ExpenseAnalysisState())
    val state: StateFlow<ExpenseAnalysisState> = _state

    fun send(intent: ExpenseAnalysisIntent) {
        when (intent) {
            is ExpenseAnalysisIntent.LoadExpenses -> {
                loadExpenses(intent.startDate, intent.endDate)
            }

            is ExpenseAnalysisIntent.GetExpensesForPdf -> {
                getExpensesForPdf(
                    onSuccess = intent.onSuccess,
                    onError = intent.onError,
                    startDate = intent.startDate,
                    endDate = intent.endDate
                )
            }
        }
    }

    private fun getExpensesForPdf(
        onSuccess: (List<Pair<LocalDate, Long>>, maxDate: LocalDate, minDate: LocalDate) -> Unit,
        onError: (String) -> Unit,
        startDate: LocalDate,
        endDate: LocalDate
    ) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                val expenses = withContext(Dispatchers.IO) {
                    getExpensesByDateRangeUseCase.invoke(startDate, endDate)
                        .groupBy { it.date }
                        .map { pair -> pair.key to pair.value.sumOf { it.amount } }
                }
                onSuccess(expenses, endDate, startDate)
            } catch (_: Exception) {
                onError("Ошибка! Попробуйте ещё раз!")
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    private var job: Job? = null
    private fun loadExpenses(startDate: LocalDate, endDate: LocalDate) {
        job?.cancel()
        job = viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, isError = false)
                val expensesForAnalysis = withContext(Dispatchers.IO) {
                    getExpensesByDateRangeUseCase.invoke(startDate, endDate)
                        .groupBy { expense -> expense.category }
                        .map { entry -> entry.key to (entry.value.sumOf { it.amount }) }
                        .sortedByDescending { it.second }
                }
                val totalAmount = withContext(Dispatchers.IO) {
                    var sum = 0L
                    expensesForAnalysis.forEach { pair -> sum += pair.second }
                    sum
                }
                _state.value = _state.value.copy(
                    expensesForAnalysis = expensesForAnalysis,
                    totalAmount = totalAmount
                )
            } catch (_: Exception) {
                _state.value = _state.value.copy(
                    isError = true,
                    expensesForAnalysis = emptyList(),
                    totalAmount = 0L
                )
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}
