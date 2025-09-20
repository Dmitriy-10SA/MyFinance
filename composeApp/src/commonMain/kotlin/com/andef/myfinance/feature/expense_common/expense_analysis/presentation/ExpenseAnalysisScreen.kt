package com.andef.myfinance.feature.expense_common.expense_analysis.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
expect fun ExpenseAnalysisScreen(
    isLightTheme: Boolean,
    navHostController: NavHostController
)