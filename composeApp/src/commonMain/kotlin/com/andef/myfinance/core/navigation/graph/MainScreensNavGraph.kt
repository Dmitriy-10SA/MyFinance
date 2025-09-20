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
import com.andef.myfinance.feature.expense_common.expense_main.presentation.ExpenseMainScreen
import com.andef.myfinance.feature.income_common.income_main.presentation.IncomeMainScreen
import com.andef.myfinance.feature.totals.presentation.TotalMainScreen
import kotlinx.datetime.LocalDate

fun NavGraphBuilder.mainScreensNavGraph(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    startDate: LocalDate,
    endDate: LocalDate,
    mainScreenIsVisible: Boolean,
    onLeftSwipe: () -> Unit,
    onRightSwipe: () -> Unit
) {
    navigation(
        route = Screen.MainScreens.route,
        startDestination = Screen.MainScreens.IncomeMainScreen.route,
        enterTransition = { fadeInAnim() },
        exitTransition = { fadeOutAnim() }
    ) {
        composable(
            route = Screen.MainScreens.IncomeMainScreen.route,
            enterTransition = { fadeInAnim() },
            exitTransition = { fadeOutAnim() }
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
                    endDate = endDate,
                    onLeftSwipe = onLeftSwipe,
                    onRightSwipe = onRightSwipe
                )
            }
        }
        composable(
            route = Screen.MainScreens.ExpenseMainScreen.route,
            enterTransition = { fadeInAnim() },
            exitTransition = { fadeOutAnim() }
        ) {
            AnimatedVisibility(
                visible = mainScreenIsVisible,
                enter = fadeInAnim(),
                exit = fadeOutAnim()
            ) {
                ExpenseMainScreen(
                    isLightTheme = isLightTheme,
                    navHostController = navHostController,
                    paddingValues = paddingValues,
                    startDate = startDate,
                    endDate = endDate,
                    onLeftSwipe = onLeftSwipe,
                    onRightSwipe = onRightSwipe
                )
            }
        }
        composable(
            route = Screen.MainScreens.TotalMainScreen.route,
            enterTransition = { fadeInAnim() },
            exitTransition = { fadeOutAnim() }
        ) {
            AnimatedVisibility(
                visible = mainScreenIsVisible,
                enter = fadeInAnim(),
                exit = fadeOutAnim()
            ) {
                TotalMainScreen(
                    isLightTheme = isLightTheme,
                    paddingValues = paddingValues,
                    startDate = startDate,
                    endDate = endDate,
                    onLeftSwipe = onLeftSwipe,
                    onRightSwipe = onRightSwipe
                )
            }
        }
    }
}