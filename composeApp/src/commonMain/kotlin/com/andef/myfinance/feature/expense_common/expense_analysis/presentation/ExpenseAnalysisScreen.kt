package com.andef.myfinance.feature.expense_common.expense_analysis.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.andef.myfinance.core.platform.common.InterstitialAdManager

@Composable
expect fun ExpenseAnalysisScreen(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    interstitialAdManager: InterstitialAdManager
)