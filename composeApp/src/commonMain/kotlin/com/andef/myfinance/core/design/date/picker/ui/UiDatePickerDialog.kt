package com.andef.myfinance.core.design.date.picker.ui

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun UiDatePickerDialog(
    isVisible: Boolean,
    isLightTheme: Boolean,
    onDismissRequest: () -> Unit,
    onOkClick: (LocalDate) -> Unit
) {
    if (isVisible) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            shape = shape,
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val selectedDate = Instant.fromEpochMilliseconds(millis)
                                .toLocalDateTime(TimeZone.currentSystemDefault())
                                .date
                            onOkClick(selectedDate)
                        }
                    },
                    enabled = datePickerState.selectedDateMillis != null,
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
            colors = colors(isLightTheme)
        ) {
            DatePicker(
                state = datePickerState,
                modifier = Modifier.verticalScroll(rememberScrollState()),
                colors = colors(isLightTheme)
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
private fun colors(isLightTheme: Boolean) = DatePickerDefaults.colors(
    containerColor = darkGrayOrWhiteColor(isLightTheme),
    titleContentColor = blackOrWhiteColor(isLightTheme),
    headlineContentColor = blackOrWhiteColor(isLightTheme),
    weekdayContentColor = blackOrWhiteColor(isLightTheme),
    subheadContentColor = blackOrWhiteColor(isLightTheme),
    navigationContentColor = blackOrWhiteColor(isLightTheme),
    yearContentColor = blackOrWhiteColor(isLightTheme),
    disabledYearContentColor = blackOrWhiteColor(isLightTheme),
    currentYearContentColor = blackOrWhiteColor(isLightTheme),
    selectedYearContentColor = White,
    disabledDayContentColor = blackOrWhiteColor(isLightTheme),
    disabledSelectedYearContainerColor = blackOrWhiteColor(isLightTheme),
    disabledSelectedDayContainerColor = darkGrayOrWhiteColor(isLightTheme),
    disabledSelectedYearContentColor = blackOrWhiteColor(isLightTheme),
    selectedDayContentColor = White,
    selectedDayContainerColor = Blue,
    selectedYearContainerColor = Blue,
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