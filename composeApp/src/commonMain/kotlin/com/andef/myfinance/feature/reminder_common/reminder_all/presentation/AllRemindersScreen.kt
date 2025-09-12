package com.andef.myfinance.feature.reminder_common.reminder_all.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.andef.myfinance.core.platform.common.PermissionManager
import com.andef.myfinance.core.platform.common.SettingsOpener

@OptIn(ExperimentalMaterial3Api::class)
@Composable
expect fun AllRemindersScreen(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    settingsOpener: SettingsOpener,
    permissionManager: PermissionManager
)