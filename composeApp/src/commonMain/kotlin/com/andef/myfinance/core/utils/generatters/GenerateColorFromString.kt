package com.andef.myfinance.core.utils.generatters

import androidx.compose.ui.graphics.Color

fun generateColorFromString(input: String): Color {
    val hash = input.hashCode()
    val r = (hash shr 16 and 0xFF).coerceIn(64, 224)
    val g = (hash shr 8 and 0xFF).coerceIn(64, 224)
    val b = (hash and 0xFF).coerceIn(64, 224)
    return Color(r, g, b)
}