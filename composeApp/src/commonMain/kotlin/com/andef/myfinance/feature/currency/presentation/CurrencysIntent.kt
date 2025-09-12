package com.andef.myfinance.feature.currency.presentation

import kotlinx.datetime.LocalDate

sealed class CurrencysIntent {
    data class LoadCurrencys(val date: LocalDate) : CurrencysIntent()
}