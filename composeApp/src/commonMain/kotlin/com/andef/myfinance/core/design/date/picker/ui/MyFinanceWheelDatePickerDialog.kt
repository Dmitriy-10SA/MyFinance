package com.andef.myfinance.core.design.date.picker.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.design.dialog.container.ui.UiDialogContainer
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.grayColor
import com.andef.myfinance.core.utils.textButtonColors
import com.andef.myfinance.core.utils.textButtonShape
import kotlinx.datetime.LocalDate
import network.chaintech.kmp_date_time_picker.utils.MAX
import network.chaintech.kmp_date_time_picker.utils.MIN
import network.chaintech.kmp_date_time_picker.utils.SelectorProperties
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import network.chaintech.kmp_date_time_picker.utils.now

@Composable
fun MyFinanceWheelDatePickerDialog(
    showDatePicker: Boolean = false,
    startDate: LocalDate = LocalDate.now(),
    minDate: LocalDate = LocalDate.MIN(),
    maxDate: LocalDate = LocalDate.MAX(),
    yearsRange: IntRange? = IntRange(1922, 2122),
    height: Dp,
    rowCount: Int = 3,
    showShortMonths: Boolean = false,
    showMonthAsNumber: Boolean = false,
    customMonthNames: List<String>? = null,
    selectedDateTextStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(
        color = LocalContentColor.current,
        fontSize = 20.sp
    ),
    defaultDateTextStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(
        color = Color.Black,
        fontSize = 18.sp
    ),
    hideHeader: Boolean = false,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onDoneClick: (snappedDate: LocalDate) -> Unit = {},
    onDateChangeListener: (snappedDate: LocalDate) -> Unit = {},
    onDismiss: () -> Unit = {},
    isLightTheme: Boolean
) {
    if (showDatePicker) {
        UiDialogContainer(isLightTheme = isLightTheme, onDismissRequest = onDismiss) {
            MyFinanceWheelDatePickerComponent.WheelDatePicker(
                startDate = startDate,
                minDate = minDate,
                maxDate = maxDate,
                yearsRange = yearsRange,
                height = height,
                rowCount = rowCount,
                customMonthNames = customMonthNames,
                showShortMonths = showShortMonths,
                showMonthAsNumber = showMonthAsNumber,
                selectedDateTextStyle = selectedDateTextStyle,
                defaultDateTextStyle = defaultDateTextStyle,
                hideHeader = hideHeader,
                selectorProperties = selectorProperties,
                onDoneClick = {
                    onDoneClick(it)
                },
                onDateChangeListener = onDateChangeListener,
                isLightTheme = isLightTheme
            )
        }
    }
}

object MyFinanceWheelDatePickerComponent {
    @Composable
    fun WheelDatePicker(
        isLightTheme: Boolean,
        startDate: LocalDate = LocalDate.now(),
        minDate: LocalDate = LocalDate.MIN(),
        maxDate: LocalDate = LocalDate.MAX(),
        yearsRange: IntRange? = IntRange(1922, 2122),
        height: Dp = 128.dp,
        rowCount: Int = 3,
        showShortMonths: Boolean = false,
        showMonthAsNumber: Boolean = false,
        selectedDateTextStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(
            color = LocalContentColor.current,
            fontSize = 18.sp
        ),
        defaultDateTextStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(
            color = Color.Black,
            fontSize = 16.sp
        ),
        hideHeader: Boolean = false,
        customMonthNames: List<String>? = null,
        selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
        onDoneClick: (snappedDate: LocalDate) -> Unit = {},
        onDateChangeListener: (snappedDate: LocalDate) -> Unit = {},
    ) {
        var selectedDate by remember { mutableStateOf(LocalDate.now()) }
        var buttonEnabled by remember { mutableStateOf(true) }

        LaunchedEffect(selectedDate) {
            if (hideHeader) {
                onDateChangeListener(selectedDate)
            }
        }

        Column {
            MyFinanceDefaultWheelDatePicker(
                selectorProperties = selectorProperties,
                rowCount = rowCount,
                height = height,
                startDate = startDate,
                minDate = minDate,
                maxDate = maxDate,
                customMonthNames = customMonthNames,
                yearsRange = yearsRange,
                showShortMonths = showShortMonths,
                showMonthAsNumber = showMonthAsNumber,
                defaultDateTextStyle = defaultDateTextStyle,
                selectedDateTextStyle = selectedDateTextStyle,
                onSnappedDate = { snappedDate ->
                    selectedDate = snappedDate.snappedLocalDate
                    snappedDate.snappedIndex
                },
                onMonthScrollInProgress = { buttonEnabled = !it },
                onDayScrollInProgress = { buttonEnabled = !it },
                onYearScrollInProgress = { buttonEnabled = !it }
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 0.5.dp,
                color = grayColor(isLightTheme = isLightTheme)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                ActionButton(
                    modifier = Modifier.matchParentSize(),
                    enabled = buttonEnabled,
                    onClick = { onDoneClick(selectedDate) },
                    isLightTheme = isLightTheme,
                    text = "Сохранить",
                    color = if (buttonEnabled) Blue else Blue.copy(alpha = 0.3f)
                )
            }
        }
    }
}

@Composable
private fun ActionButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
    isLightTheme: Boolean,
    text: String,
    color: Color
) {
    TextButton(
        enabled = enabled,
        modifier = modifier,
        onClick = onClick,
        shape = textButtonShape(topEnd = 0.dp, topStart = 0.dp),
        colors = textButtonColors(isLightTheme = isLightTheme)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}