package com.andef.myfinance.feature.totals.presentation

import kotlinx.datetime.LocalDate

sealed class TotalsMainIntent {
    data class SubscribeForAllIncomesAndExpenses(
        val startDate: LocalDate,
        val endDate: LocalDate
    ) : TotalsMainIntent()
}