package com.andef.myfinance.core.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.andef.myfinance.core.navigation.routes.Screen
import com.andef.myfinance.core.platform.backup.BackupManager
import com.andef.myfinance.core.platform.common.LinkOpener
import com.andef.myfinance.core.utils.anims.fadeInAnim
import com.andef.myfinance.core.utils.anims.fadeOutAnim
import com.andef.myfinance.core.utils.anims.slideInLeftHorizontalAnim
import com.andef.myfinance.core.utils.anims.slideInRightHorizontalAnim
import com.andef.myfinance.core.utils.anims.slideOutLeftHorizontalAnim
import com.andef.myfinance.core.utils.anims.slideOutRightHorizontalAnim
import com.andef.myfinance.feature.auth.presentation.AuthScreen
import com.andef.myfinance.feature.backup.presentation.start.BackupStartScreen

fun NavGraphBuilder.startScreensNavGraph(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    backupManager: BackupManager,
    linkOpener: LinkOpener,
    currentRoute: String?
) {
    navigation(
        route = Screen.StartScreens.route,
        startDestination = Screen.StartScreens.UsernameScreen.route,
        enterTransition = { fadeInAnim() },
        exitTransition = { fadeOutAnim() }
    ) {
        composable(
            route = Screen.StartScreens.UsernameScreen.route,
            enterTransition = { fadeInAnim() },
            popEnterTransition = { slideInRightHorizontalAnim() },
            exitTransition = {
                if (currentRoute != Screen.StartScreens.BackupStartScreen.route) fadeOutAnim()
                else slideOutLeftHorizontalAnim()
            }
        ) {
            AuthScreen(
                isLightTheme = isLightTheme,
                paddingValues = paddingValues,
                navHostController = navHostController
            )
        }
        composable(
            route = Screen.StartScreens.BackupStartScreen.route,
            enterTransition = { slideInLeftHorizontalAnim() },
            exitTransition = { fadeOutAnim() },
            popExitTransition = { slideOutRightHorizontalAnim() }
        ) {
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