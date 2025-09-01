package com.andef.myfinance.core.navigation.graph

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.andef.myfinance.core.navigation.routes.Screen
import com.andef.myfinance.core.utils.anims.fadeInAnim
import com.andef.myfinance.core.utils.anims.fadeOutAnim
import com.andef.myfinance.core.utils.anims.slideInRightHorizontalAnim
import com.andef.myfinance.core.utils.anims.slideOutLeftHorizontalAnim
import com.andef.myfinance.feature.income_common.income_main.presentation.IncomeMainScreen
import kotlinx.datetime.LocalDate

fun NavGraphBuilder.mainScreensNavGraph(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    startDate: LocalDate,
    endDate: LocalDate,
    currentRoute: String?,
    previousRoute: String?,
    mainScreenIsVisible: Boolean
) {
    navigation(
        route = Screen.MainScreens.route,
        startDestination = Screen.MainScreens.IncomeMainScreen.route,
        enterTransition = { fadeInAnim() },
        exitTransition = { fadeOutAnim() }
    ) {
        composable(
            route = Screen.MainScreens.IncomeMainScreen.route,
            enterTransition = {
                if (
                    previousRoute == Screen.MainScreens.ExpenseMainScreen.route ||
                    previousRoute == Screen.MainScreens.TotalMainScreen.route
                ) {
                    slideInRightHorizontalAnim()
                } else {
                    fadeInAnim()
                }
            },
            exitTransition = {
                if (
                    currentRoute == Screen.MainScreens.ExpenseMainScreen.route ||
                    currentRoute == Screen.MainScreens.TotalMainScreen.route
                ) {
                    slideOutLeftHorizontalAnim()
                } else {
                    fadeOutAnim()
                }
            }
        ) {
            AnimatedVisibility(
                visible = mainScreenIsVisible,
                enter = fadeInAnim(),
                exit = fadeOutAnim()
            ) {
                IncomeMainScreen(
                    isLightTheme = isLightTheme,
                    navHostController = navHostController,
                    paddingValues = paddingValues,
                    startDate = startDate,
                    endDate = endDate
                )
            }
        }
        composable(route = Screen.MainScreens.ExpenseMainScreen.route) {
            AnimatedVisibility(
                visible = mainScreenIsVisible,
                enter = fadeInAnim(),
                exit = fadeOutAnim()
            ) {

            }
//            ExpenseMainScreen(
//                isLightTheme = isLightTheme,
//                navHostController = navHostController,
//                viewModelFactory = viewModelFactory,
//                paddingValues = paddingValues,
//                startDate = startDate,
//                endDate = endDate
//            )
        }
        composable(route = Screen.MainScreens.TotalMainScreen.route) {
            AnimatedVisibility(
                visible = mainScreenIsVisible,
                enter = fadeInAnim(),
                exit = fadeOutAnim()
            ) {

            }
//            TotalMainScreen(
//                isLightTheme = isLightTheme,
//                navHostController = navHostController,
//                viewModelFactory = viewModelFactory,
//                paddingValues = paddingValues,
//                startDate = startDate,
//                endDate = endDate
//            )
        }
    }
}