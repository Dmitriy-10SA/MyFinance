package com.andef.myfinance.core.di.common

import com.andef.myfinance.core.platform.common.InterstitialAdManager
import com.andef.myfinance.core.platform.common.IosInterstitialAdManager
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun interstitialAdManagerModule(id: String): Module = module {
    single { IosInterstitialAdManager(id = id) }.bind<InterstitialAdManager>()
}