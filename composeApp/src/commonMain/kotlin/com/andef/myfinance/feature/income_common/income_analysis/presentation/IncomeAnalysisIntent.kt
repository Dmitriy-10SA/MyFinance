package com.andef.myfinance.feature.income_common.income_analysis.presentation

import kotlinx.datetime.LocalDate

sealed class IncomeAnalysisIntent {
    data class LoadIncomes(
        val startDate: LocalDate,
        val endDate: LocalDate
    ) : IncomeAnalysisIntent()

    data class GetIncomesForPdf(
        val onSuccess: (List<Pair<LocalDate, Double>>, maxDate: LocalDate, minDate: LocalDate) -> Unit,
        val onError: (String) -> Unit,
        val startDate: LocalDate,
        val endDate: LocalDate
    ) : IncomeAnalysisIntent()
}