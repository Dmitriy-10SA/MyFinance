package com.andef.myfinance

import androidx.compose.ui.window.ComposeUIViewController
import com.andef.myfinance.app.App
import com.andef.myfinance.app.initKoin
import com.andef.myfinance.core.platform.common.IosInterstitialAdManager

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App(IosInterstitialAdManager(INTERSTITIAL_ID))
}

private const val INTERSTITIAL_ID = "demo-interstitial-yandex" //R-M-17151552-5