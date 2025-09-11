package com.andef.myfinance.feature.income_common.income_analysis.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.andef.myfinance.core.platform.common.InterstitialAdManager

@Composable
expect fun IncomeAnalysisScreen(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    interstitialAdManager: InterstitialAdManager
)