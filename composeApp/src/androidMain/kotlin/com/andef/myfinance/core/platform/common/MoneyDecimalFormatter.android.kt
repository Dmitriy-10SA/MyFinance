package com.andef.myfinance.core.platform.common

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class AndroidMoneyDecimalFormatter : MoneyDecimalFormatter {
    private val dfs = DecimalFormatSymbols(
        Locale.Builder().setLanguage("ru").setRegion("RU").build()
    ).apply {
        groupingSeparator = ' '
        decimalSeparator = '.'
    }

    private val formatter = DecimalFormat("#,##0.00", dfs)

    override fun format(number: Double): String {
        return formatter.format(number)
    }
}