package com.andef.myfinance.core.design.legend.row.ui

import androidx.compose.ui.graphics.Color

data class UiLegendAmountItem(
    val color: Color,
    val amount: Double,
    val title: String,
    val percent: Float,
    val isIncome: Boolean
)