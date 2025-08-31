package com.andef.myfinance

import androidx.compose.ui.window.ComposeUIViewController
import com.andef.myfinance.app.App
import com.andef.myfinance.core.di.initKoin

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) { App() }