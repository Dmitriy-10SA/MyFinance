package com.andef.myfinance.core.utils

import androidx.navigation.NavHostController

fun NavHostController.navigateWithSaveState(popUpToRoute: String, whereNavigateRoute: String) {
    this.navigate(whereNavigateRoute) {
        popUpTo(popUpToRoute) {
            saveState = true
        }
        restoreState = true
        launchSingleTop = true
    }
}