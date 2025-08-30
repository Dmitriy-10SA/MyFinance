package com.andef.myfinance.core.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.andef.myfinance.core.navigation.routes.Screen

fun NavGraphBuilder.startScreensNavGraph(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation(
        route = Screen.StartScreens.route,
        startDestination = Screen.StartScreens.UsernameScreen.route
    ) {
        composable(route = Screen.StartScreens.UsernameScreen.route) {
            //UsernameScreen(isLightTheme, navHostController, viewModelFactory, paddingValues)
        }
        composable(route = Screen.StartScreens.BackupStartScreen.route) {
            //BackupStartScreen(isLightTheme, navHostController, viewModelFactory, paddingValues)
        }
    }
}