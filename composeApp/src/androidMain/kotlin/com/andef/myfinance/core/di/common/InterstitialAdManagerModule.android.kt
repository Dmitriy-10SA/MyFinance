package com.andef.myfinance.core.di.common

import com.andef.myfinance.MainActivity
import com.andef.myfinance.core.platform.common.AndroidInterstitialAdManager
import com.andef.myfinance.core.platform.common.InterstitialAdManager
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun interstitialAdManagerModule(id: String): Module = module {
    scope<MainActivity> {
        scoped {
            AndroidInterstitialAdManager(activity = get(), id = id)
        }.bind<InterstitialAdManager>()
    }
}