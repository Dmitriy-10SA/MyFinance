package com.andef.myfinance.core.design.piechart.ui

object UiPieChartUtils {
    fun calculateAngle(
        sliceLength: Float,
        totalLength: Float,
        progress: Float
    ): Float {
        return 360.0f * (sliceLength * progress) / totalLength
    }
}