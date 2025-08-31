package com.andef.myfinance.core.utils

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun showSnackbar(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    message: String,
    beforeShowCallback: () -> Unit = {},
    afterShowCallback: () -> Unit = {}
) {
    scope.launch {
        beforeShowCallback()
        snackbarHostState.currentSnackbarData?.dismiss()
        snackbarHostState.showSnackbar(
            message = message,
            withDismissAction = true
        )
        afterShowCallback()
    }
}