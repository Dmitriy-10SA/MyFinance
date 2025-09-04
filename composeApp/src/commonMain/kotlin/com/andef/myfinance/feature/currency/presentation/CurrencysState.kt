package com.andef.myfinance.feature.currency.presentation

import com.andef.myfinance.feature.currency.domain.entities.CurrencyRub

data class CurrencysState(
    val currencys: List<Pair<CurrencyRub, Float>> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)