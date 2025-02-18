package com.andef.myfinance.presentation.formatter

object PriceAndIncomeFormatter {
    fun formatPrice(price: Double): String {
        return "%.2f".format(price)
    }
}