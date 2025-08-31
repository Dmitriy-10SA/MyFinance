package com.andef.myfinance.core.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.savedstate.read
import com.andef.myfinance.core.navigation.routes.Screen
import kotlinx.datetime.LocalDate

@Composable
fun AppNavGraph(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    isFirstStart: Boolean,
    startDate: LocalDate,
    endDate: LocalDate
) {
    NavHost(
        navController = navHostController,
        startDestination = if (isFirstStart) {
            Screen.StartScreens.route
        } else {
            Screen.MainScreens.route
        }
    ) {
        startScreensNavGraph(
            isLightTheme = isLightTheme,
            navHostController = navHostController,
            paddingValues = paddingValues
        )
        mainScreensNavGraph(
            navHostController = navHostController,
            paddingValues = paddingValues,
            isLightTheme = isLightTheme,
            startDate = startDate,
            endDate = endDate
        )
        composable(route = Screen.IncomeAnalysisScreen.route) {
//            IncomeAnalysisScreen(
//                isLightTheme = isLightTheme,
//                navHostController = navHostController,
//                viewModelFactory = viewModelFactory,
//                paddingValues = paddingValues
//            )
        }
        composable(route = Screen.IncomeAddScreen.route) {
//            IncomeAddScreen(null, isLightTheme, navHostController, viewModelFactory, paddingValues)
        }
        composable(
            route = Screen.IncomeScreen.route,
            arguments = listOf(navArgument(Screen.ID_PARAM) { type = NavType.LongType })
        ) {
            val id = it.arguments?.read { getLong(Screen.ID_PARAM) } ?: throw IllegalArgumentException()
//            IncomeAddScreen(id, isLightTheme, navHostController, viewModelFactory, paddingValues)
        }
        composable(route = Screen.ExpenseAnalysisScreen.route) {
//            ExpenseAnalysisScreen(
//                isLightTheme = isLightTheme,
//                navHostController = navHostController,
//                viewModelFactory = viewModelFactory,
//                paddingValues
//            )
        }
        composable(route = Screen.ExpenseAddScreen.route) {
//            ExpenseAddScreen(null, isLightTheme, navHostController, viewModelFactory, paddingValues)
        }
        composable(
            route = Screen.ExpenseScreen.route,
            arguments = listOf(navArgument(Screen.ID_PARAM) { type = NavType.LongType })
        ) {
            val id = it.arguments?.read { getLong(Screen.ID_PARAM) } ?: throw IllegalArgumentException()
//            ExpenseAddScreen(id, isLightTheme, navHostController, viewModelFactory, paddingValues)
        }
        composable(route = Screen.CurrencysScreen.route) {
//            CurrencysScreen(
//                isLightTheme = isLightTheme,
//                navHostController = navHostController,
//                viewModelFactory = viewModelFactory,
//                paddingValues = paddingValues
//            )
        }
        composable(route = Screen.AllRemindersScreen.route) {
//            AllRemindersScreen(
//                isLightTheme = isLightTheme,
//                navHostController = navHostController,
//                viewModelFactory = viewModelFactory,
//                paddingValues = paddingValues
//            )
        }
        composable(route = Screen.ReminderAddScreen.route) {
//            ReminderAddScreen(
//                reminderId = null,
//                isLightTheme = isLightTheme,
//                navHostController = navHostController,
//                viewModelFactory = viewModelFactory,
//                paddingValues = paddingValues
//            )
        }
        composable(
            route = Screen.ReminderScreen.route,
            arguments = listOf(navArgument(Screen.ID_PARAM) { type = NavType.LongType })
        ) {
            val id = it.arguments?.read { getLong(Screen.ID_PARAM) } ?: throw IllegalArgumentException()
//            ReminderAddScreen(
//                reminderId = id,
//                isLightTheme = isLightTheme,
//                navHostController = navHostController,
//                viewModelFactory = viewModelFactory,
//                paddingValues = paddingValues
//            )
        }
        composable(route = Screen.BackupMainScreen.route) {
//            BackupMainScreen(
//                isLightTheme = isLightTheme,
//                navHostController = navHostController,
//                viewModelFactory = viewModelFactory,
//                paddingValues = paddingValues
//            )
        }
        composable(route = Screen.IncomeCategoryAddScreen.route) {
//            IncomeCategoryAddScreen(
//                isLightTheme = isLightTheme,
//                navHostController = navHostController,
//                viewModelFactory = viewModelFactory,
//                paddingValues = paddingValues
//            )
        }
        composable(route = Screen.ExpenseCategoryAddScreen.route) {
//            ExpenseCategoryAddScreen(
//                isLightTheme = isLightTheme,
//                navHostController = navHostController,
//                viewModelFactory = viewModelFactory,
//                paddingValues = paddingValues
//            )
        }
    }
}