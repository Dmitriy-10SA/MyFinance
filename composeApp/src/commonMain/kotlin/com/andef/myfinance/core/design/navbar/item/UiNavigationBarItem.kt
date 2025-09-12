package com.andef.myfinance.core.design.navbar.item

import androidx.compose.ui.graphics.painter.Painter

open class UiNavigationBarItem(
    val icon: Painter,
    val contentDescription: String,
    val title: String,
    val route: String
)