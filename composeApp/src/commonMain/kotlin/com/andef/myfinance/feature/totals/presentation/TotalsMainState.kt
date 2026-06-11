package com.andef.myfinance.feature.totals.presentation

data class TotalsMainState(
    val totalIncomesAmount: Long = 0L,
    val totalExpensesAmount: Long = 0L,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)

