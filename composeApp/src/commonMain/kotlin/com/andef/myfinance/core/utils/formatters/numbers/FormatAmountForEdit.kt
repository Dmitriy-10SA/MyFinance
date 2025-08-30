package com.andef.myfinance.core.utils.formatters.numbers

import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.round

fun formatAmountForEdit(value: Double): String {
    val rounded = round(value * 100) / 100
    val intPart = floor(rounded).toLong()
    val fracPart = abs(((rounded - intPart) * 100).toInt())
    val fracStr = fracPart.toString().padStart(2, '0')

    return "$intPart,$fracStr"
}