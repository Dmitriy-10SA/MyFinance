package com.andef.myfinance.core.design.piechart.ui

import androidx.compose.ui.graphics.Color

data class UiPieChartData(
    val slices: List<Slice>
) {
    internal val totalSize: Float
        get() {
            var total = 0f
            slices.forEach { total += it.value }
            return total
        }

    data class Slice(
        val value: Float,
        val color: Color
    )
}