package com.andef.myfinance.core.utils.formatters.numbers

import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.round

fun Float.format(digits: Int = 2): String {
    val absThis = abs(this)
    val factor = 10.0.pow(digits)
    val rounded = round(absThis * factor) / factor
    val intPart = floor(rounded).toLong()
    val fracPart = abs(((rounded - intPart) * factor).toInt())
    val fracStr = fracPart.toString().padStart(digits, '0')
    return if (this < 0) {
        "-$intPart.${fracStr}"
    } else {
        "$intPart.${fracStr}"
    }
}