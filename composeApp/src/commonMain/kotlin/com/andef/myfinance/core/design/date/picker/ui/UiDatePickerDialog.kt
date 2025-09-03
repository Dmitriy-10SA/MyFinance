package com.andef.myfinance.core.design.date.picker.ui

import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalDate

@Composable
expect fun UiDatePickerDialog(
    isVisible: Boolean,
    isLightTheme: Boolean,
    onDismissRequest: () -> Unit,
    onOkClick: (LocalDate) -> Unit
)