package com.andef.myfinance.core.design.ads.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.andef.myfinance.core.design.ads.type.UiAdsType

@Composable
expect fun UiAds(id: String, modifier: Modifier = Modifier, type: UiAdsType)