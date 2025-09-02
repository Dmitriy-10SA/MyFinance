package com.andef.myfinance.core.platform.common

interface MoneyDecimalFormatter {
    fun format(number: Double): String
}