package com.andef.myfinance.core.design.time.picker.ui

import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalTime

@Composable
expect fun UiTimePickerDialog(
    isVisible: Boolean,
    isLightTheme: Boolean,
    onDismissRequest: () -> Unit,
    onOkClick: (LocalTime) -> Unit
)