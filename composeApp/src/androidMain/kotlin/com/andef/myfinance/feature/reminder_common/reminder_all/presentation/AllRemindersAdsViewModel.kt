package com.andef.myfinance.feature.reminder_common.reminder_all.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.nativeads.NativeAd
import com.yandex.mobile.ads.nativeads.NativeAdLoadListener
import com.yandex.mobile.ads.nativeads.NativeAdLoader
import com.yandex.mobile.ads.nativeads.NativeAdRequestConfiguration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AllRemindersAdsViewModel(private val application: Application) : AndroidViewModel(application) {
    private var nativeAdLoader: NativeAdLoader? = null

    private val _adViews = MutableStateFlow(listOf<NativeAd>())
    val adViews: StateFlow<List<NativeAd>> = _adViews.asStateFlow()

    private fun createNativeAdLoader(): NativeAdLoader {
        return nativeAdLoader ?: NativeAdLoader(application).apply {
            setNativeAdLoadListener(object : NativeAdLoadListener {
                override fun onAdFailedToLoad(error: AdRequestError) {
                }

                override fun onAdLoaded(nativeAd: NativeAd) {
                    val newAddViews = _adViews.value.toMutableList()
                    newAddViews.add(nativeAd)
                    _adViews.value = newAddViews
                }
            })
        }
    }

    init {
        nativeAdLoader = createNativeAdLoader()
        repeat(2) {
            nativeAdLoader?.loadAd(
                NativeAdRequestConfiguration.Builder(ID)
                    .setContextTags(
                        listOf("напоминания","доходы", "финансы", "бюджет", "деньги", "инвестиции")
                    )
                    .setContextQuery("улучшение памяти, запоминания")
                    .build()
            )
        }
    }

    companion object Companion {
        private const val ID = "R-M-17151552-7"
    }
}