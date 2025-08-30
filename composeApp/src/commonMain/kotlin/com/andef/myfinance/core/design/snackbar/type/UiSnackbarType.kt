package com.andef.myfinance.core.design.snackbar.type

import androidx.compose.ui.graphics.Color
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.Red

sealed class UiSnackbarType(val containerColor: Color) {
    data object Error : UiSnackbarType(containerColor = Red)
    data object Success : UiSnackbarType(containerColor = Blue)
}