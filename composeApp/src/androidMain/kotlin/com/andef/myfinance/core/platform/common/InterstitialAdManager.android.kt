package com.andef.myfinance.core.platform.common

import android.app.Activity
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.interstitial.InterstitialAd
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoadListener
import com.yandex.mobile.ads.interstitial.InterstitialAdLoader

class AndroidInterstitialAdManager(
    private val activity: Activity,
    private val id: String
): InterstitialAdManager {
    private var interstitialAd: InterstitialAd? = null
    private var interstitialAdLoader: InterstitialAdLoader? = null

    init {
        interstitialAdLoader = InterstitialAdLoader(activity).apply {
            setAdLoadListener(object : InterstitialAdLoadListener {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    this@AndroidInterstitialAdManager.interstitialAd = interstitialAd
                }

                override fun onAdFailedToLoad(error: AdRequestError) {}
            })
        }
        loadAd()
    }

    override fun loadAd() {
        val adRequestConfiguration = AdRequestConfiguration.Builder(id).build()
        interstitialAdLoader?.loadAd(adRequestConfiguration)
    }


    override fun showAd(afterShow: () -> Unit) {
        interstitialAd?.apply {
            setAdEventListener(object : InterstitialAdEventListener {
                override fun onAdShown() {}
                override fun onAdFailedToShow(adError: AdError) {
                    cleanup()
                    afterShow()
                }
                override fun onAdDismissed() {
                    cleanup()
                    afterShow()
                }
                override fun onAdClicked() {}
                override fun onAdImpression(impressionData: ImpressionData?) {}
            })
            show(activity)
        } ?: run {
            afterShow()
        }
    }

    private fun cleanup() {
        interstitialAd?.setAdEventListener(null)
        interstitialAd = null
        loadAd()
    }

    override fun destroy() {
        interstitialAdLoader?.setAdLoadListener(null)
        interstitialAdLoader = null
        interstitialAd?.setAdEventListener(null)
        interstitialAd = null
    }
}