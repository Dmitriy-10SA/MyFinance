package com.andef.myfinance.core.design.date.picker.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.Red
import com.andef.myfinance.core.utils.White
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.darkGrayOrWhiteColor
import com.andef.myfinance.core.utils.grayColor
import kotlinx.datetime.toKotlinLocalDate
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun UiRangeDatePickerDialog(
    isVisible: Boolean,
    isLightTheme: Boolean,
    onDismissRequest: () -> Unit,
    onOkClick: (kotlinx.datetime.LocalDate, kotlinx.datetime.LocalDate) -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()
    if (isVisible) {
        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            shape = shape,
            confirmButton = {
                TextButton(
                    onClick = {
                        dateRangePickerState.selectedStartDateMillis?.let { startDate ->
                            dateRangePickerState.selectedEndDateMillis?.let { endDate ->
                                val selectedStartDate = Instant.ofEpochMilli(startDate)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                                val selectedEndDate = Instant.ofEpochMilli(endDate)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                                onOkClick(
                                    selectedStartDate.toKotlinLocalDate(),
                                    selectedEndDate.toKotlinLocalDate()
                                )
                            }
                        }
                    },
                    enabled = dateRangePickerState.selectedStartDateMillis != null &&
                            dateRangePickerState.selectedEndDateMillis != null,
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
            DateRangePicker(
                state = dateRangePickerState,
                title = {},
                headline = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        textAlign = TextAlign.Center,
                        text = buildString {
                            append(dateRangePickerState.selectedStartDateMillis?.let {
                                Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault())
                                    .toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                            } ?: "Начало")
                            append(" - ")
                            append(dateRangePickerState.selectedEndDateMillis?.let {
                                Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault())
                                    .toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                            } ?: "Конец")
                        },
                        fontSize = 16.sp,
                        color = blackOrWhiteColor(isLightTheme)
                    )
                },
                colors = colors(isLightTheme),
                dateFormatter = russianDateFormatter
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private val russianDateFormatter = object : DatePickerFormatter {
    private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale("ru"))
    private val monthYearFormatter = DateTimeFormatter.ofPattern("LLLL yyyy", Locale("ru"))

    override fun formatDate(
        dateMillis: Long?,
        locale: CalendarLocale,
        forContentDescription: Boolean
    ): String? {
        if (dateMillis == null) return null
        val date = Instant.ofEpochMilli(dateMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        return date.format(dateFormatter)
    }

    override fun formatMonthYear(
        monthMillis: Long?,
        locale: CalendarLocale
    ): String? {
        if (monthMillis == null) return null
        val date = Instant.ofEpochMilli(monthMillis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        return date.format(monthYearFormatter)
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