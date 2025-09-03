package com.andef.myfinance.feature.expense_common.expense_analysis.presentation

import kotlinx.datetime.LocalDate

sealed class ExpenseAnalysisIntent {
    data class LoadExpenses(
        val startDate: LocalDate,
        val endDate: LocalDate
    ) : ExpenseAnalysisIntent()

    data class GetExpensesForPdf(
        val onSuccess: (List<Pair<LocalDate, Double>>, maxDate: LocalDate, minDate: LocalDate) -> Unit,
        val onError: (String) -> Unit,
        val startDate: LocalDate,
        val endDate: LocalDate
    ) : ExpenseAnalysisIntent()
}