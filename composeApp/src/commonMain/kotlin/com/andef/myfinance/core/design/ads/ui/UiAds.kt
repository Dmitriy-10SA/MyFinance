package com.andef.myfinance.core.design.ads.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.andef.myfinance.core.design.ads.type.UiAdsType

@Composable
expect fun UiAds(
    isLightTheme: Boolean,
    id: String,
    modifier: Modifier = Modifier,
    type: UiAdsType,
    contextTags: List<String> = listOf(
        "бюджет",
        "доходы",
        "аналитика",
        "инвестирование",
        "финансовые советы"
    )
)