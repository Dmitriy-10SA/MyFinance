package com.andef.myfinance.core.design.ads.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.andef.myfinance.core.design.ads.type.UiAdsType
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.banner.BannerAdSize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import kotlin.math.roundToInt

@Composable
actual fun UiAds(isLightTheme: Boolean, id: String, modifier: Modifier, type: UiAdsType) {
    when (type) {
        UiAdsType.StickyBanner -> UiStickBanner(
            isLightTheme = isLightTheme,
            id = id,
            modifier = modifier
        )
    }
}

@Composable
private fun UiStickBanner(isLightTheme: Boolean, id: String, modifier: Modifier) {
    var bannerView by remember { mutableStateOf<BannerAdView?>(null) }
    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            BannerAdView(ctx).apply {
                val adWidthPx = ctx.resources.displayMetrics.widthPixels
                val adWidthDp = (adWidthPx / ctx.resources.displayMetrics.density).roundToInt()
                val adSize = BannerAdSize.stickySize(ctx, adWidthDp)

                setAdUnitId(id)
                setAdSize(adSize)
                val config = AdRequest.Builder()
                    .setParameters(mapOf("theme" to if (isLightTheme) "light" else "dark"))
                    .build()
                setBannerAdEventListener(object : BannerAdEventListener {
                    override fun onAdLoaded() {
                        Log.d("YandexAds", "Banner loaded")
                    }

                    override fun onAdFailedToLoad(error: AdRequestError) {
                        Log.e("YandexAds", "Ad failed: $error")
                    }

                    override fun onAdClicked() {}
                    override fun onImpression(impressionData: ImpressionData?) {}
                    override fun onLeftApplication() {}
                    override fun onReturnedToApplication() {}
                })
                loadAd(config)
                bannerView = this
            }
        },
        update = { view ->
            bannerView = view
        }
    )

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                bannerView?.destroy()
                bannerView = null
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            bannerView?.destroy()
        }
    }
}