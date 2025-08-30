package com.andef.myfinance.core.design.time.picker.ui

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
import kotlinx.datetime.LocalTime
import network.chaintech.kmp_date_time_picker.utils.MAX
import network.chaintech.kmp_date_time_picker.utils.MIN
import network.chaintech.kmp_date_time_picker.utils.SelectorProperties
import network.chaintech.kmp_date_time_picker.utils.TimeFormat
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import network.chaintech.kmp_date_time_picker.utils.now

@Composable
fun MyFinanceWheelTimePickerDialog(
    isLightTheme: Boolean,
    showTimePicker: Boolean = false,
    startTime: LocalTime = LocalTime.now(),
    minTime: LocalTime = LocalTime.MIN(),
    maxTime: LocalTime = LocalTime.MAX(),
    timeFormat: TimeFormat = TimeFormat.HOUR_24,
    height: Dp,
    rowCount: Int = 3,
    selectedTextStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(
        color = LocalContentColor.current,
        fontSize = 20.sp
    ),
    defaultTextStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(
        color = Color.Black,
        fontSize = 18.sp
    ),
    hideHeader: Boolean = false,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onDoneClick: (snappedDate: LocalTime) -> Unit = {},
    onTimeChangeListener: (snappedDate: LocalTime) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    if (showTimePicker) {
        UiDialogContainer(isLightTheme = isLightTheme, onDismissRequest = onDismiss) {
            MyFinanceWheelTimePickerComponent.MyFinanceWheelTimePicker(
                timeFormat = timeFormat,
                selectorProperties = selectorProperties,
                rowCount = rowCount,
                height = height,
                hideHeader = hideHeader,
                startTime = startTime,
                minTime = minTime,
                maxTime = maxTime,
                selectedTextStyle = selectedTextStyle,
                defaultTextStyle = defaultTextStyle,
                onDoneClick = {
                    onDoneClick(it)
                },
                onTimeChangeListener = onTimeChangeListener,
                isLightTheme = isLightTheme
            )
        }
    }
}

object MyFinanceWheelTimePickerComponent {
    @Composable
    fun MyFinanceWheelTimePicker(
        isLightTheme: Boolean,
        startTime: LocalTime = LocalTime.now(),
        minTime: LocalTime = LocalTime.MIN(),
        maxTime: LocalTime = LocalTime.MAX(),
        timeFormat: TimeFormat = TimeFormat.HOUR_24,
        height: Dp,
        rowCount: Int = 3,
        selectedTextStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(
            color = LocalContentColor.current,
            fontSize = 20.sp
        ),
        defaultTextStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(
            color = Color.Black,
            fontSize = 18.sp
        ),
        hideHeader: Boolean = false,
        selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
        onDoneClick: (snappedDate: LocalTime) -> Unit = {},
        onTimeChangeListener: (snappedDate: LocalTime) -> Unit = {},
    ) {
        var selectedDate by remember { mutableStateOf(LocalTime.now()) }
        var buttonEnabled by remember { mutableStateOf(true) }

        LaunchedEffect(selectedDate) {
            if (hideHeader) {
                onTimeChangeListener(selectedDate)
            }
        }

        Column {
            MyFinanceDefaultWheelTimePicker(
                timeFormat = timeFormat,
                selectorProperties = selectorProperties,
                rowCount = rowCount,
                height = height,
                startTime = startTime,
                minTime = minTime,
                maxTime = maxTime,
                defaultTextStyle = defaultTextStyle,
                selectedTextStyle = selectedTextStyle,
                onSnappedTime = { snappedTime, _ ->
                    selectedDate = snappedTime.snappedLocalTime
                    snappedTime.snappedIndex
                },
                onHourScrollInProgress = { buttonEnabled = !it },
                onMinuteScrollInProgress = { buttonEnabled = !it },
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