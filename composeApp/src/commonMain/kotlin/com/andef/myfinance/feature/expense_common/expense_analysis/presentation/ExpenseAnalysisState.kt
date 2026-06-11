package com.andef.myfinance.feature.expense_common.expense_analysis.presentation

import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel

data class ExpenseAnalysisState(
    val expensesForAnalysis: List<Pair<ExpenseCategoryModel, Long>> = emptyList(),
    val totalAmount: Long = 0L,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
