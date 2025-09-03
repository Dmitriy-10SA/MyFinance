package com.andef.myfinance.core.design.date.picker.ui

import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalDate

@Composable
expect fun UiRangeDatePickerDialog(
    isVisible: Boolean,
    isLightTheme: Boolean,
    onDismissRequest: () -> Unit,
    onOkClick: (LocalDate, LocalDate) -> Unit
)