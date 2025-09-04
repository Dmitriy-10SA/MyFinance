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
import com.andef.myfinance.core.platform.backup.BackupManager
import com.andef.myfinance.core.platform.common.LinkOpener
import com.andef.myfinance.core.platform.common.MoneyDecimalFormatter
import com.andef.myfinance.core.utils.anims.fadeInAnim
import com.andef.myfinance.core.utils.anims.fadeOutAnim
import com.andef.myfinance.feature.expense_common.expense_add_and_change.presentation.ExpenseAddAndChangeScreen
import com.andef.myfinance.feature.expense_common.expense_analysis.presentation.ExpenseAnalysisScreen
import com.andef.myfinance.feature.income_common.income_add_and_change.presentation.IncomeAddAndChangeScreen
import com.andef.myfinance.feature.income_common.income_analysis.presentation.IncomeAnalysisScreen
import com.andef.myfinance.feature.income_common.income_category.presentation.IncomeCategoryAddScreen
import kotlinx.datetime.LocalDate
import org.koin.compose.getKoin

@Composable
fun AppNavGraph(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    isFirstStart: Boolean,
    startDate: LocalDate,
    endDate: LocalDate,
    currentRoute: String?,
    previousRoute: String?,
    mainScreenIsVisible: Boolean
) {
    val backupManager = getKoin().get<BackupManager>()
    val linkOpener = getKoin().get<LinkOpener>()
    val moneyDecimalFormatter = getKoin().get<MoneyDecimalFormatter>()

    NavHost(
        navController = navHostController,
        startDestination = if (isFirstStart) {
            Screen.StartScreens.route
        } else {
            Screen.MainScreens.route
        },
        enterTransition = { fadeInAnim() },
        exitTransition = { fadeOutAnim() }
    ) {
        startScreensNavGraph(
            isLightTheme = isLightTheme,
            navHostController = navHostController,
            paddingValues = paddingValues,
            backupManager = backupManager,
            linkOpener = linkOpener,
            currentRoute = currentRoute
        )
        mainScreensNavGraph(
            navHostController = navHostController,
            paddingValues = paddingValues,
            isLightTheme = isLightTheme,
            startDate = startDate,
            endDate = endDate,
            mainScreenIsVisible = mainScreenIsVisible
        )
        composable(
            route = Screen.IncomeAnalysisScreen.route,
            enterTransition = { fadeInAnim() },
            exitTransition = { fadeOutAnim() }
        ) {
            IncomeAnalysisScreen(
                isLightTheme = isLightTheme,
                navHostController = navHostController,
                paddingValues = paddingValues
            )
        }
        composable(
            route = Screen.IncomeAddScreen.route,
            enterTransition = { fadeInAnim() },
            exitTransition = { fadeOutAnim() }
        ) {
            IncomeAddAndChangeScreen(
                incomeId = null,
                isLightTheme = isLightTheme,
                navHostController = navHostController,
                paddingValues = paddingValues,
                moneyDecimalFormatter = moneyDecimalFormatter
            )
        }
        composable(
            route = Screen.IncomeScreen.route,
            arguments = listOf(navArgument(Screen.ID_PARAM) { type = NavType.LongType }),
            enterTransition = { fadeInAnim() },
            exitTransition = { fadeOutAnim() }
        ) {
            val id = it.arguments?.read { getLong(Screen.ID_PARAM) }
                ?: throw IllegalArgumentException()
            IncomeAddAndChangeScreen(
                incomeId = id,
                isLightTheme = isLightTheme,
                navHostController = navHostController,
                paddingValues = paddingValues,
                moneyDecimalFormatter = moneyDecimalFormatter
            )
        }
        composable(
            route = Screen.ExpenseAnalysisScreen.route,
            enterTransition = { fadeInAnim() },
            exitTransition = { fadeOutAnim() }
        ) {
            ExpenseAnalysisScreen(
                isLightTheme = isLightTheme,
                navHostController = navHostController
            )
        }
        composable(
            route = Screen.ExpenseAddScreen.route,
            enterTransition = { fadeInAnim() },
            exitTransition = { fadeOutAnim() }
        ) {
            ExpenseAddAndChangeScreen(
                expenseId = null,
                isLightTheme = isLightTheme,
                navHostController = navHostController,
                paddingValues = paddingValues,
                moneyDecimalFormatter = moneyDecimalFormatter
            )
        }
        composable(
            route = Screen.ExpenseScreen.route,
            arguments = listOf(navArgument(Screen.ID_PARAM) { type = NavType.LongType }),
            enterTransition = { fadeInAnim() },
            exitTransition = { fadeOutAnim() }
        ) {
            val id = it.arguments?.read { getLong(Screen.ID_PARAM) }
                ?: throw IllegalArgumentException()
            ExpenseAddAndChangeScreen(
                expenseId = id,
                isLightTheme = isLightTheme,
                navHostController = navHostController,
                paddingValues = paddingValues,
                moneyDecimalFormatter = moneyDecimalFormatter
            )
        }
        composable(
            route = Screen.CurrencysScreen.route,
            enterTransition = { fadeInAnim() },
            exitTransition = { fadeOutAnim() }
        ) {
//            CurrencysScreen(
//                isLightTheme = isLightTheme,
//                navHostController = navHostController,
//                viewModelFactory = viewModelFactory,
//                paddingValues = paddingValues
//            )
        }
        composable(
            route = Screen.AllRemindersScreen.route,
            enterTransition = { fadeInAnim() },
            exitTransition = { fadeOutAnim() }
        ) {
//            AllRemindersScreen(
//                isLightTheme = isLightTheme,
//                navHostController = navHostController,
//                viewModelFactory = viewModelFactory,
//                paddingValues = paddingValues
//            )
        }
        composable(
            route = Screen.ReminderAddScreen.route,
            enterTransition = { fadeInAnim() },
            exitTransition = { fadeOutAnim() }
        ) {
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
            arguments = listOf(navArgument(Screen.ID_PARAM) { type = NavType.LongType }),
            enterTransition = { fadeInAnim() },
            exitTransition = { fadeOutAnim() }
        ) {
            val id =
                it.arguments?.read { getLong(Screen.ID_PARAM) }
                    ?: throw IllegalArgumentException()
//            ReminderAddScreen(
//                reminderId = id,
//                isLightTheme = isLightTheme,
//                navHostController = navHostController,
//                viewModelFactory = viewModelFactory,
//                paddingValues = paddingValues
//            )
        }
        composable(
            route = Screen.BackupMainScreen.route,
            enterTransition = { fadeInAnim() },
            exitTransition = { fadeOutAnim() }
        ) {
//            BackupMainScreen(
//                isLightTheme = isLightTheme,
//                navHostController = navHostController,
//                viewModelFactory = viewModelFactory,
//                paddingValues = paddingValues
//            )
        }
        composable(
            route = Screen.IncomeCategoryAddScreen.route,
            enterTransition = { fadeInAnim() },
            exitTransition = { fadeOutAnim() }
        ) {
            IncomeCategoryAddScreen(
                isLightTheme = isLightTheme,
                navHostController = navHostController
            )
        }
        composable(
            route = Screen.ExpenseCategoryAddScreen.route,
            enterTransition = { fadeInAnim() },
            exitTransition = { fadeOutAnim() }
        ) {
//            ExpenseCategoryAddScreen(
//                isLightTheme = isLightTheme,
//                navHostController = navHostController,
//                viewModelFactory = viewModelFactory,
//                paddingValues = paddingValues
//            )
        }
    }
}