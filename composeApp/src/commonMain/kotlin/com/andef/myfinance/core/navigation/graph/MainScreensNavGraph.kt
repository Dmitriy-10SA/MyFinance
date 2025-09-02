package com.andef.myfinance.core.navigation.graph

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.andef.myfinance.core.navigation.routes.Screen
import com.andef.myfinance.core.platform.common.Logger
import com.andef.myfinance.core.utils.anims.fadeInAnim
import com.andef.myfinance.core.utils.anims.fadeOutAnim
import com.andef.myfinance.core.utils.anims.slideInLeftHorizontalAnim
import com.andef.myfinance.core.utils.anims.slideInRightHorizontalAnim
import com.andef.myfinance.core.utils.anims.slideOutLeftHorizontalAnim
import com.andef.myfinance.core.utils.anims.slideOutRightHorizontalAnim
import com.andef.myfinance.feature.expense_common.expense_main.presentation.ExpenseMainScreen
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
    mainScreenIsVisible: Boolean,
    logger: Logger
) {
    val incomeMainRoute = Screen.MainScreens.IncomeMainScreen.route
    val expenseMainRoute = Screen.MainScreens.ExpenseMainScreen.route
    val totalMainRoute = Screen.MainScreens.TotalMainScreen.route
    navigation(
        route = Screen.MainScreens.route,
        startDestination = Screen.MainScreens.IncomeMainScreen.route,
        enterTransition = { fadeInAnim() },
        exitTransition = { fadeOutAnim() }
    ) {
        composable(
            route = Screen.MainScreens.IncomeMainScreen.route,
            enterTransition = {
                logger.d(
                    "NAV:",
                    "Income enter: prev: $previousRoute, curr: $currentRoute, next: $nextCurrentRoute"
                )
                if (
                    nextCurrentRoute == incomeMainRoute &&
                    (currentRoute == expenseMainRoute || currentRoute == totalMainRoute)
                ) {
                    slideInRightHorizontalAnim()
                } else {
                    fadeInAnim()
                }
            },
            exitTransition = {
                logger.d(
                    "NAV:",
                    "Income exit: prev: $previousRoute, curr: $currentRoute, next: $nextCurrentRoute"
                )
                if (
                    currentRoute == incomeMainRoute &&
                    (nextCurrentRoute == expenseMainRoute || nextCurrentRoute == totalMainRoute)
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
        composable(
            route = Screen.MainScreens.ExpenseMainScreen.route,
            enterTransition = {
                logger.d(
                    "NAV:",
                    "Expense enter: prev: $previousRoute, curr: $currentRoute, next: $nextCurrentRoute"
                )
                if (currentRoute == incomeMainRoute && nextCurrentRoute == expenseMainRoute) {
                    slideInLeftHorizontalAnim()
                } else if (currentRoute == totalMainRoute && nextCurrentRoute == expenseMainRoute) {
                    slideInRightHorizontalAnim()
                } else {
                    fadeInAnim()
                }
            },
            exitTransition = {
                logger.d(
                    "NAV:",
                    "Expense exit: prev: $previousRoute, curr: $currentRoute, next: $nextCurrentRoute"
                )
                if (currentRoute == expenseMainRoute && nextCurrentRoute == incomeMainRoute) {
                    slideOutRightHorizontalAnim()
                } else if (currentRoute == expenseMainRoute && nextCurrentRoute == totalMainRoute) {
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
                ExpenseMainScreen(
                    isLightTheme = isLightTheme,
                    navHostController = navHostController,
                    paddingValues = paddingValues,
                    startDate = startDate,
                    endDate = endDate
                )
            }
        }
        composable(
            route = Screen.MainScreens.TotalMainScreen.route,
            enterTransition = {
                logger.d(
                    "NAV:",
                    "Total enter: prev: $previousRoute, curr: $currentRoute, next: $nextCurrentRoute"
                )
                if (
                    (currentRoute == incomeMainRoute || currentRoute == expenseMainRoute) &&
                    nextCurrentRoute == totalMainRoute
                ) {
                    slideInLeftHorizontalAnim()
                } else {
                    fadeInAnim()
                }
            },
            exitTransition = {
                logger.d(
                    "NAV:", "Total exit: prev: $previousRoute, curr: $currentRoute, next: $nextCurrentRoute"
                )
                if (
                    currentRoute == totalMainRoute &&
                    (nextCurrentRoute == incomeMainRoute || nextCurrentRoute == expenseMainRoute)
                ) {
                    slideOutRightHorizontalAnim()
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
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("TSDSG")
                }
            }
        }
    }
}