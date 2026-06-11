package com.andef.myfinance.core.utils.formatters.numbers

import kotlin.math.abs

fun formatPriceRuble(value: Long): String {
    val intPart = value / 100
    val fracPart = abs(value % 100)
    val fracStr = fracPart.toString().padStart(2, '0')

    val intStr = intPart.toString()
        .reversed()
        .chunked(3)
        .joinToString(" ")
        .reversed()

    return "$intStr.$fracStr₽"
}
