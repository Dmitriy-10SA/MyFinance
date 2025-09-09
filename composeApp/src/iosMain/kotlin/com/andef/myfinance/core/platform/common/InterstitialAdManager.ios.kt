package com.andef.myfinance.core.platform.common

class IosInterstitialAdManager(private val id: String) : InterstitialAdManager {
    override fun loadAd() {}
    override fun showAd(afterShow: () -> Unit) { afterShow() }
    override fun destroy() {}
}