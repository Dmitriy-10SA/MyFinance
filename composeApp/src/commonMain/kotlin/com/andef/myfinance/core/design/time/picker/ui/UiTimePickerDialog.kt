package com.andef.myfinance.core.design.time.picker.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.blackOrWhiteColor
import kotlinx.datetime.LocalTime
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import network.chaintech.kmp_date_time_picker.utils.now

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiTimePickerDialog(
    isVisible: Boolean,
    isLightTheme: Boolean,
    startTime: LocalTime = LocalTime.now(),
    onDismissRequest: () -> Unit,
    onOkClick: (LocalTime) -> Unit
) {
    MyFinanceWheelTimePickerDialog(
        showTimePicker = isVisible,
        startTime = startTime,
        selectedTextStyle = TextStyle(
            color = blackOrWhiteColor(isLightTheme = isLightTheme),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        ),
        rowCount = 5,
        onDismiss = onDismissRequest,
        height = 140.dp,
        defaultTextStyle = TextStyle(
            color = blackOrWhiteColor(isLightTheme = isLightTheme),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        ),
        selectorProperties = WheelPickerDefaults.selectorProperties(
            borderColor = Blue.copy(alpha = 0.5f)
        ),
        isLightTheme = isLightTheme,
        onDoneClick = { onOkClick(it) }
    )
}