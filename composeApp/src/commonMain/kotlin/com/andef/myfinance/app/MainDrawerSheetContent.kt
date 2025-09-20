package com.andef.myfinance.app

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
expect fun MainDrawerSheetContent(
    navHostController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    viewModel: AppViewModel,
    username: String,
    isLightTheme: Boolean
)