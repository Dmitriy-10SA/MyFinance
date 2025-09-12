package com.andef.myfinance.feature.expense_common.expense_main.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
expect fun ExpenseMainScreen(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    startDate: LocalDate,
    endDate: LocalDate
)