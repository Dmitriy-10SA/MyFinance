package com.andef.myfinance.core.platform.common

interface InterstitialAdManager {
    fun loadAd()
    fun showAd(afterShow: () -> Unit)
    fun destroy()
}