package com.andef.myfinance.core.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.andef.myfinance.core.navigation.routes.Screen
import com.andef.myfinance.core.platform.backup.BackupManager
import com.andef.myfinance.core.platform.common.LinkOpener
import com.andef.myfinance.feature.auth.presentation.AuthScreen
import com.andef.myfinance.feature.backup.presentation.start.BackupStartScreen

fun NavGraphBuilder.startScreensNavGraph(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    backupManager: BackupManager,
    linkOpener: LinkOpener
) {
    navigation(
        route = Screen.StartScreens.route,
        startDestination = Screen.StartScreens.UsernameScreen.route
    ) {
        composable(route = Screen.StartScreens.UsernameScreen.route) {
            AuthScreen(
                isLightTheme = isLightTheme,
                paddingValues = paddingValues,
                navHostController = navHostController
            )
        }
        composable(route = Screen.StartScreens.BackupStartScreen.route) {
            BackupStartScreen(
                isLightTheme = isLightTheme,
                navHostController = navHostController,
                paddingValues = paddingValues,
                backupManager = backupManager,
                linkOpener = linkOpener
            )
        }
    }
}