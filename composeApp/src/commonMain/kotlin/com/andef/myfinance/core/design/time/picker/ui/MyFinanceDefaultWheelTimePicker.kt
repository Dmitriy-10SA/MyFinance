package com.andef.myfinance.core.design.time.picker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalTime
import network.chaintech.kmp_date_time_picker.utils.AmPm
import network.chaintech.kmp_date_time_picker.utils.AmPmHour
import network.chaintech.kmp_date_time_picker.utils.AmPmValue
import network.chaintech.kmp_date_time_picker.utils.Hour
import network.chaintech.kmp_date_time_picker.utils.MAX
import network.chaintech.kmp_date_time_picker.utils.MIN
import network.chaintech.kmp_date_time_picker.utils.Minute
import network.chaintech.kmp_date_time_picker.utils.SelectorProperties
import network.chaintech.kmp_date_time_picker.utils.TimeFormat
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import network.chaintech.kmp_date_time_picker.utils.amPmHourToHour24
import network.chaintech.kmp_date_time_picker.utils.amPmValueFromTime
import network.chaintech.kmp_date_time_picker.utils.localTimeToAmPmHour
import network.chaintech.kmp_date_time_picker.utils.now
import network.chaintech.kmp_date_time_picker.utils.truncateTo
import network.chaintech.kmp_date_time_picker.utils.withHour
import network.chaintech.kmp_date_time_picker.utils.withMinute

@Composable
fun MyFinanceDefaultWheelTimePicker(
    modifier: Modifier = Modifier,
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
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(borderColor = Color.Transparent),
    onSnappedTime: (snappedTime: MyFinanceSnappedTime, timeFormat: TimeFormat) -> Int? = { _, _ -> null },
    onHourScrollInProgress: (Boolean) -> Unit,
    onMinuteScrollInProgress: (Boolean) -> Unit
) {
    var snappedTime by remember { mutableStateOf(startTime.truncateTo(DateTimeUnit.SECOND)) }

    val hours = (0..23).map { Hour(it.toString().padStart(2, '0'), it, it) }
    val amPmHours = (1..12).map { AmPmHour(it.toString(), it, it - 1) }
    val minutes = (0..59).map { Minute(it.toString().padStart(2, '0'), it, it) }

    val amPms = listOf(
        AmPm("AM", AmPmValue.AM, 0),
        AmPm("PM", AmPmValue.PM, 1)
    )

    var snappedAmPm by remember {
        mutableStateOf(amPms.find { it.value == amPmValueFromTime(startTime) } ?: amPms[0])
    }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        if (selectorProperties.enabled().value) {
            HorizontalDivider(
                modifier = Modifier.padding(bottom = (height / rowCount)),
                thickness = 0.5.dp,
                color = selectorProperties.borderColor().value
            )
            HorizontalDivider(
                modifier = Modifier.padding(top = (height / rowCount)),
                thickness = 0.5.dp,
                color = selectorProperties.borderColor().value
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Hour picker
            MyFinanceWheelTextPicker(
                modifier = Modifier.width(80.dp),
                startIndex = if (timeFormat == TimeFormat.HOUR_24)
                    hours.find { it.value == startTime.hour }?.index ?: 0
                else
                    amPmHours.find { it.value == localTimeToAmPmHour(startTime) }?.index ?: 0,
                height = height,
                texts = if (timeFormat == TimeFormat.HOUR_24) hours.map { it.text } else amPmHours.map { it.text },
                rowCount = rowCount,
                defaultTextStyle = defaultTextStyle,
                selectedTextStyle = selectedTextStyle,
                onScrollInProgress = onHourScrollInProgress,
                onScrollFinished = { snappedIndex ->
                    val newHour = if (timeFormat == TimeFormat.HOUR_24)
                        hours.find { it.index == snappedIndex }?.value
                    else
                        amPmHourToHour24(
                            amPmHours.find { it.index == snappedIndex }?.value ?: 0,
                            snappedTime.minute,
                            snappedAmPm.value
                        )
                    newHour?.let {
                        val newTime = snappedTime.withHour(it)
                        if (newTime in minTime..maxTime) snappedTime = newTime
                        val newIndex = if (timeFormat == TimeFormat.HOUR_24)
                            hours.find { it.value == snappedTime.hour }?.index
                        else
                            amPmHours.find { it.value == localTimeToAmPmHour(snappedTime) }?.index
                        newIndex?.let {
                            onSnappedTime(MyFinanceSnappedTime.Hour(snappedTime, it), timeFormat)
                        }
                    }
                    if (timeFormat == TimeFormat.HOUR_24)
                        hours.find { it.value == snappedTime.hour }?.index
                    else
                        amPmHours.find { it.value == localTimeToAmPmHour(snappedTime) }?.index
                }
            )

            Text(
                ":",
                style = selectedTextStyle,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            // Minute picker
            MyFinanceWheelTextPicker(
                modifier = Modifier.width(80.dp),
                startIndex = minutes.find { it.value == startTime.minute }?.index ?: 0,
                height = height,
                texts = minutes.map { it.text },
                rowCount = rowCount,
                selectedTextStyle = selectedTextStyle,
                defaultTextStyle = defaultTextStyle,
                onScrollInProgress = onMinuteScrollInProgress,
                onScrollFinished = { snappedIndex ->
                    val newMinute = minutes.find { it.index == snappedIndex }?.value
                    val newHour = if (timeFormat == TimeFormat.HOUR_24)
                        snappedTime.hour
                    else
                        amPmHourToHour24(
                            localTimeToAmPmHour(snappedTime),
                            snappedTime.minute,
                            snappedAmPm.value
                        )
                    if (newMinute != null) {
                        val newTime = snappedTime.withMinute(newMinute).withHour(newHour)
                        if (newTime in minTime..maxTime) snappedTime = newTime
                        minutes.find { it.value == snappedTime.minute }?.index?.let {
                            onSnappedTime(MyFinanceSnappedTime.Minute(snappedTime, it), timeFormat)
                        }
                    }
                    minutes.find { it.value == snappedTime.minute }?.index
                }
            )
        }
    }
}