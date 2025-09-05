package com.andef.myfinance.core.design.time.picker.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.Red
import com.andef.myfinance.core.utils.White
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.darkGrayOrWhiteColor
import com.andef.myfinance.core.utils.grayColor
import kotlinx.datetime.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiTimePickerDialog(
    isVisible: Boolean,
    isLightTheme: Boolean,
    onDismissRequest: () -> Unit,
    onOkClick: (LocalTime) -> Unit
) {
    if (isVisible) {
        val timePickerState = rememberTimePickerState()
        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            shape = shape,
            confirmButton = {
                TextButton(
                    onClick = {
                        val selectedTime =
                            LocalTime(timePickerState.hour, timePickerState.minute)
                        onOkClick(selectedTime)
                    },
                    shape = textShape,
                    colors = textColors(isLightTheme = isLightTheme)
                ) {
                    Text(text = "ОК", fontSize = 14.sp)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismissRequest,
                    shape = textShape,
                    colors = textColors(isLightTheme = isLightTheme)
                ) {
                    Text(text = "Отмена", fontSize = 14.sp)
                }
            },
            colors = dialogColors(isLightTheme)
        ) {
            TimePicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp),
                state = timePickerState,
                colors = timePickerColors(isLightTheme)
            )
        }
    }
}

private val textShape = RoundedCornerShape(16.dp)
private val shape = RoundedCornerShape(28.dp)

@Composable
private fun textColors(isLightTheme: Boolean) = ButtonDefaults.textButtonColors(
    containerColor = Color.Transparent,
    contentColor = blackOrWhiteColor(isLightTheme),
    disabledContentColor = blackOrWhiteColor(isLightTheme).copy(alpha = 0.3f),
    disabledContainerColor = Color.Transparent
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun timePickerColors(isLightTheme: Boolean) = TimePickerDefaults.colors(
    clockDialColor = darkGrayOrWhiteColor(isLightTheme),
    selectorColor = Blue,
    periodSelectorBorderColor = blackOrWhiteColor(isLightTheme),
    containerColor = darkGrayOrWhiteColor(isLightTheme),
    periodSelectorSelectedContainerColor = Blue,
    periodSelectorUnselectedContainerColor = Color.Transparent,
    periodSelectorSelectedContentColor = White,
    periodSelectorUnselectedContentColor = blackOrWhiteColor(isLightTheme),
    timeSelectorSelectedContainerColor = Blue,
    clockDialSelectedContentColor = White,
    clockDialUnselectedContentColor = blackOrWhiteColor(isLightTheme),
    timeSelectorUnselectedContainerColor = Color.Transparent,
    timeSelectorSelectedContentColor = White,
    timeSelectorUnselectedContentColor = blackOrWhiteColor(isLightTheme)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun dialogColors(isLightTheme: Boolean) = DatePickerDefaults.colors(
    containerColor = darkGrayOrWhiteColor(isLightTheme),
    titleContentColor = blackOrWhiteColor(isLightTheme),
    headlineContentColor = blackOrWhiteColor(isLightTheme),
    weekdayContentColor = blackOrWhiteColor(isLightTheme),
    subheadContentColor = blackOrWhiteColor(isLightTheme),
    navigationContentColor = blackOrWhiteColor(isLightTheme),
    yearContentColor = blackOrWhiteColor(isLightTheme),
    disabledYearContentColor = blackOrWhiteColor(isLightTheme),
    currentYearContentColor = blackOrWhiteColor(isLightTheme),
    selectedYearContentColor = blackOrWhiteColor(isLightTheme),
    disabledDayContentColor = blackOrWhiteColor(isLightTheme),
    disabledSelectedYearContainerColor = blackOrWhiteColor(isLightTheme),
    disabledSelectedDayContainerColor = darkGrayOrWhiteColor(isLightTheme),
    disabledSelectedYearContentColor = blackOrWhiteColor(isLightTheme),
    selectedDayContentColor = White,
    selectedDayContainerColor = Blue,
    selectedYearContainerColor = blackOrWhiteColor(isLightTheme),
    todayContentColor = blackOrWhiteColor(isLightTheme),
    todayDateBorderColor = blackOrWhiteColor(isLightTheme),
    dayContentColor = blackOrWhiteColor(isLightTheme),
    disabledSelectedDayContentColor = blackOrWhiteColor(isLightTheme),
    dayInSelectionRangeContainerColor = Blue,
    dayInSelectionRangeContentColor = White,
    dividerColor = Color.Transparent,
    dateTextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = blackOrWhiteColor(isLightTheme),
        focusedContainerColor = darkGrayOrWhiteColor(isLightTheme),
        focusedLabelColor = blackOrWhiteColor(isLightTheme),
        focusedPlaceholderColor = grayColor(isLightTheme),
        unfocusedTextColor = blackOrWhiteColor(isLightTheme),
        unfocusedContainerColor = darkGrayOrWhiteColor(isLightTheme),
        unfocusedLabelColor = blackOrWhiteColor(isLightTheme),
        unfocusedPlaceholderColor = grayColor(isLightTheme),
        cursorColor = blackOrWhiteColor(isLightTheme),
        selectionColors = TextSelectionColors(
            handleColor = Blue,
            backgroundColor = Blue.copy(alpha = 0.2f)
        ),
        focusedIndicatorColor = grayColor(isLightTheme),
        unfocusedIndicatorColor = grayColor(isLightTheme),
        focusedSupportingTextColor = grayColor(isLightTheme),
        unfocusedSupportingTextColor = grayColor(isLightTheme),
        errorTextColor = blackOrWhiteColor(isLightTheme),
        errorLabelColor = blackOrWhiteColor(isLightTheme),
        errorCursorColor = blackOrWhiteColor(isLightTheme),
        errorContainerColor = darkGrayOrWhiteColor(isLightTheme),
        errorIndicatorColor = grayColor(isLightTheme),
        errorPlaceholderColor = grayColor(isLightTheme),
        errorSupportingTextColor = Red
    )
)