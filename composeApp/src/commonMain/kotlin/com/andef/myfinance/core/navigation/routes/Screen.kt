package com.andef.myfinance.core.navigation.routes

sealed class Screen(val route: String) {
    data object StartScreens: Screen(route = START_SCREENS) {
        data object UsernameScreen : Screen(USERNAME_SCREEN)
        data object BackupStartScreen : Screen(BACKUP_START_SCREEN)

        private const val BACKUP_START_SCREEN = "backup-start-screen"
        private const val USERNAME_SCREEN = "username_screen"
    }

    data object MainScreens: Screen(route = MAIN_SCREENS) {
        data object ExpenseMainScreen : Screen(route = EXPENSE_MAIN_SCREEN)
        data object IncomeMainScreen : Screen(route = INCOME_MAIN_SCREEN)
        data object TotalMainScreen : Screen(route = TOTAL_MAIN_SCREEN)

        private const val EXPENSE_MAIN_SCREEN = "expense-main-screen"
        private const val INCOME_MAIN_SCREEN = "income-main-screen"
        private const val TOTAL_MAIN_SCREEN = "total-main-screen"
    }

    data object IncomeAnalysisScreen: Screen(route = INCOME_ANALYSIS_SCREEN)
    data object IncomeAddScreen: Screen(route = INCOME_ADD_SCREEN)
    data object IncomeScreen: Screen(route = "$INCOME_SCREEN/{$ID_PARAM}") {
        fun passId(id: Long): String = "$INCOME_SCREEN/$id"
    }

    data object ExpenseAnalysisScreen: Screen(route = EXPENSE_ANALYSIS_SCREEN)
    data object ExpenseAddScreen: Screen(route = EXPENSE_ADD_SCREEN)
    data object ExpenseScreen: Screen(route = "$EXPENSE_SCREEN/{$ID_PARAM}") {
        fun passId(id: Long): String = "$EXPENSE_SCREEN/$id"
    }

    data object CurrencysScreen: Screen(route = CURRENCYS_SCREEN)

    data object AllRemindersScreen: Screen(route = ALL_REMINDERS_SCREEN)
    data object ReminderAddScreen: Screen(route = REMINDER_ADD_SCREEN)
    data object ReminderScreen: Screen(route = "$REMINDER_SCREEN/{$ID_PARAM}") {
        fun passId(id: Long): String = "$REMINDER_SCREEN/$id"
    }

    data object BackupMainScreen : Screen(BACKUP_MAIN_SCREEN)

    data object IncomeCategoryAddScreen: Screen(route = INCOME_CATEGORY_ADD_SCREEN)

    data object ExpenseCategoryAddScreen: Screen(route = EXPENSE_CATEGORY_ADD_SCREEN)

    companion object {
        private const val START_SCREENS = "start-screens"
        private const val MAIN_SCREENS = "main-screens"
        private const val INCOME_ADD_SCREEN = "income-add-screen"
        private const val INCOME_SCREEN = "income-screen"
        private const val EXPENSE_ADD_SCREEN = "expense-add-screen"
        private const val EXPENSE_SCREEN = "expense-screen"
        private const val EXPENSE_ANALYSIS_SCREEN = "expense-analysis-screen"
        private const val INCOME_ANALYSIS_SCREEN = "income-analysis-screen"
        private const val CURRENCYS_SCREEN = "currencys-screen"
        private const val ALL_REMINDERS_SCREEN = "all-reminders-screen"
        private const val REMINDER_ADD_SCREEN = "reminder-add-screen"
        private const val REMINDER_SCREEN = "reminder-screen"
        private const val BACKUP_MAIN_SCREEN = "backup-main-screen"
        private const val INCOME_CATEGORY_ADD_SCREEN = "income-category-add-screen"
        private const val EXPENSE_CATEGORY_ADD_SCREEN = "expense-category-add-screen"

        const val ID_PARAM = "id-param"
    }
}