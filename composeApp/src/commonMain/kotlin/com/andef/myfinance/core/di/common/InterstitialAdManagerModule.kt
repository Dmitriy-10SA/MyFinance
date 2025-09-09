package com.andef.myfinance.core.di.common

import org.koin.core.module.Module

expect fun interstitialAdManagerModule(id: String = INTERSTITIAL_ID): Module

private const val INTERSTITIAL_ID = "demo-interstitial-yandex" //R-M-17151552-5