package com.andef.myfinance.core.utils.formatters.numbers

import kotlin.math.abs

fun formatAmountForEdit(value: Long): String {
    val intPart = value / 100
    val fracPart = abs(value % 100)
    val fracStr = fracPart.toString().padStart(2, '0')

    return "$intPart,$fracStr"
}
