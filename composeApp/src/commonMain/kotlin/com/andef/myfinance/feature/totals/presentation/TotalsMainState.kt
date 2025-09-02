package com.andef.myfinance.feature.totals.presentation

data class TotalsMainState(
    val totalIncomesAmount: Double = 0.0,
    val totalExpensesAmount: Double = 0.0,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

