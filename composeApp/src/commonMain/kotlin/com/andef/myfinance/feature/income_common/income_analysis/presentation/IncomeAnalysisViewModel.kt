package com.andef.myfinance.feature.income_common.income_analysis.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.core.domain.income_common.income.usecases.GetIncomesByDateRangeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate

class IncomeAnalysisViewModel (
    private val getIncomesByDateRangeUseCase: GetIncomesByDateRangeUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(IncomeAnalysisState())
    val state: StateFlow<IncomeAnalysisState> = _state.asStateFlow()

    fun send(intent: IncomeAnalysisIntent) {
        when (intent) {
            is IncomeAnalysisIntent.LoadIncomes -> {
                loadIncomes(intent.startDate, intent.endDate)
            }

            is IncomeAnalysisIntent.GetIncomesForPdf -> {
                getIncomesForPdf(
                    onSuccess = intent.onSuccess,
                    onError = intent.onError,
                    startDate = intent.startDate,
                    endDate = intent.endDate
                )
            }
        }
    }

    private fun getIncomesForPdf(
        onSuccess: (List<Pair<LocalDate, Double>>, maxDate: LocalDate, minDate: LocalDate) -> Unit,
        onError: (String) -> Unit,
        startDate: LocalDate,
        endDate: LocalDate
    ) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                val incomes = withContext(Dispatchers.IO) {
                    getIncomesByDateRangeUseCase.invoke(startDate, endDate)
                        .groupBy { it.date }
                        .map { pair -> pair.key to pair.value.sumOf { it.amount } }
                }
                onSuccess(incomes, endDate, startDate)
            } catch (_: Exception) {
                onError("Ошибка! Попробуйте ещё раз!")
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    private var job: Job? = null
    private fun loadIncomes(startDate: LocalDate, endDate: LocalDate) {
        job?.cancel()
        job = viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, isError = false)
                val incomesForAnalysis = withContext(Dispatchers.IO) {
                    getIncomesByDateRangeUseCase.invoke(startDate, endDate)
                        .groupBy { income -> income.category }
                        .map { entry -> entry.key to (entry.value.sumOf { it.amount }) }
                }
                val totalAmount = withContext(Dispatchers.IO) {
                    var sum = 0.0
                    incomesForAnalysis.forEach { pair -> sum += pair.second }
                    sum
                }
                _state.value = _state.value.copy(
                    incomesForAnalysis = incomesForAnalysis,
                    totalAmount = totalAmount
                )
            } catch (_: Exception) {
                _state.value = _state.value.copy(
                    isError = true,
                    incomesForAnalysis = emptyList(),
                    totalAmount = 0.0
                )
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}