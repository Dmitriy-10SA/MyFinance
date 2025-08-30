package com.andef.myfinance.core.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.andef.myfinance.core.navigation.routes.Screen
import kotlinx.datetime.LocalDate

fun NavGraphBuilder.mainScreensNavGraph(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    mainContentIsVisible: Boolean,
    startDate: LocalDate,
    endDate: LocalDate
) {
    navigation(
        route = Screen.MainScreens.route,
        startDestination = Screen.MainScreens.IncomeMainScreen.route
    ) {
        composable(route = Screen.MainScreens.IncomeMainScreen.route) {
//            IncomeMainScreen(
//                isLightTheme = isLightTheme,
//                navHostController = navHostController,
//                viewModelFactory = viewModelFactory,
//                paddingValues = paddingValues,
//                startDate = startDate,
//                endDate = endDate
//            )
        }
        composable(route = Screen.MainScreens.ExpenseMainScreen.route) {
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