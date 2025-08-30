package com.andef.myfinance.core.design.date.picker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.number
import network.chaintech.kmp_date_time_picker.ui.datepicker.SnappedDate
import network.chaintech.kmp_date_time_picker.utils.MAX
import network.chaintech.kmp_date_time_picker.utils.MIN
import network.chaintech.kmp_date_time_picker.utils.SelectorProperties
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import network.chaintech.kmp_date_time_picker.utils.Year
import network.chaintech.kmp_date_time_picker.utils.calculateDayOfMonths
import network.chaintech.kmp_date_time_picker.utils.capitalize
import network.chaintech.kmp_date_time_picker.utils.now
import network.chaintech.kmp_date_time_picker.utils.shortMonths
import network.chaintech.kmp_date_time_picker.utils.withDayOfMonth
import network.chaintech.kmp_date_time_picker.utils.withMonth
import network.chaintech.kmp_date_time_picker.utils.withYear

@Composable
fun MyFinanceDefaultWheelDatePicker(
    modifier: Modifier = Modifier,
    startDate: LocalDate = LocalDate.now(),
    minDate: LocalDate = LocalDate.MIN(),
    maxDate: LocalDate = LocalDate.MAX(),
    yearsRange: IntRange? = IntRange(1922, 2122),
    height: Dp = 128.dp,
    rowCount: Int = 3,
    customMonthNames: List<String>? = null,
    showShortMonths: Boolean = false,
    showMonthAsNumber: Boolean = false, // Added flag to show month as a number
    selectedDateTextStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(
        color = LocalContentColor.current,
        fontSize = 20.sp
    ),
    defaultDateTextStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(
        color = Color.Black,
        fontSize = 18.sp
    ),
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedDate: (snappedDate: SnappedDate) -> Int? = { _ -> null },
    onMonthScrollInProgress: (Boolean) -> Unit,
    onDayScrollInProgress: (Boolean) -> Unit,
    onYearScrollInProgress: (Boolean) -> Unit
) {
    var snappedDate by remember { mutableStateOf(startDate) }

    var dayOfMonths = calculateDayOfMonths(snappedDate.month.number, snappedDate.year)

    val months = (1..12).map { monthIndex ->
        val name = when {
            customMonthNames != null && customMonthNames.size == 12 -> customMonthNames[monthIndex - 1]
            showMonthAsNumber -> monthIndex.toString()
            showShortMonths -> shortMonths(monthIndex)
            else -> Month(monthIndex).name.capitalize()
        }

        network.chaintech.kmp_date_time_picker.utils.Month(
            text = name,
            value = monthIndex,
            index = monthIndex - 1
        )
    }

    val years = yearsRange?.map {
        Year(
            text = it.toString(),
            value = it,
            index = yearsRange.indexOf(it)
        )
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if (selectorProperties.enabled().value) {
            HorizontalDivider(
                modifier = Modifier.padding(bottom = (height / rowCount)),
                thickness = (0.5).dp,
                color = selectorProperties.borderColor().value
            )
            HorizontalDivider(
                modifier = Modifier.padding(top = (height / rowCount)),
                thickness = (0.5).dp,
                color = selectorProperties.borderColor().value
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            MyFinanceWheelTextPicker(
                modifier = Modifier.width(120.dp),
                startIndex = months.find { it.value == startDate.month.number }?.index ?: 0,
                height = height,
                texts = months.map { it.text },
                rowCount = rowCount,
                selectedDateTextStyle = selectedDateTextStyle,
                defaultDateTextStyle = defaultDateTextStyle,
                contentAlignment = Alignment.CenterStart,
                onScrollInProgress = onMonthScrollInProgress,
                onScrollFinished = { snappedIndex ->

                    val newMonth = months.find { it.index == snappedIndex }?.value

                    newMonth?.let {
                        val newDate = snappedDate.withMonth(newMonth)

                        if (newDate.compareTo(minDate) >= 0 && newDate.compareTo(maxDate) <= 0) {
                            snappedDate = newDate
                        }

                        dayOfMonths =
                            calculateDayOfMonths(snappedDate.month.number, snappedDate.year)

                        val newIndex = months.find { it.value == snappedDate.month.number }?.index

                        newIndex?.let {
                            onSnappedDate(
                                SnappedDate.Month(
                                    localDate = snappedDate,
                                    index = newIndex
                                )
                            )?.let { return@MyFinanceWheelTextPicker it }
                        }
                    }

                    return@MyFinanceWheelTextPicker months.find { it.value == snappedDate.month.number }?.index
                }
            )

            MyFinanceWheelTextPicker(
                modifier = Modifier.width(30.dp),
                startIndex = dayOfMonths.find { it.value == startDate.day }?.index ?: 0,
                height = height,
                texts = dayOfMonths.map { it.text },
                rowCount = rowCount,
                defaultDateTextStyle = defaultDateTextStyle,
                selectedDateTextStyle = selectedDateTextStyle,
                onScrollInProgress = onDayScrollInProgress,
                onScrollFinished = { snappedIndex ->

                    val newDayOfMonth = dayOfMonths.find { it.index == snappedIndex }?.value

                    newDayOfMonth?.let {
                        val newDate = snappedDate.withDayOfMonth(newDayOfMonth)

                        if (newDate.compareTo(minDate) >= 0 && newDate.compareTo(maxDate) <= 0) {
                            snappedDate = newDate
                        }

                        val newIndex =
                            dayOfMonths.find { it.value == snappedDate.day }?.index

                        newIndex?.let {
                            onSnappedDate(
                                SnappedDate.DayOfMonth(
                                    localDate = snappedDate,
                                    index = newIndex
                                )
                            )?.let { return@MyFinanceWheelTextPicker it }
                        }
                    }

                    return@MyFinanceWheelTextPicker dayOfMonths.find { it.value == snappedDate.day }?.index
                }
            )

            years?.let { years ->
                MyFinanceWheelTextPicker(
                    modifier = Modifier.width(60.dp),
                    startIndex = years.find { it.value == startDate.year }?.index ?: 0,
                    height = height,
                    texts = years.map { it.text },
                    rowCount = rowCount,
                    defaultDateTextStyle = defaultDateTextStyle,
                    selectedDateTextStyle = selectedDateTextStyle,
                    contentAlignment = Alignment.CenterEnd,
                    onScrollInProgress = onYearScrollInProgress,
                    onScrollFinished = { snappedIndex ->

                        val newYear = years.find { it.index == snappedIndex }?.value

                        newYear?.let {

                            val newDate = snappedDate.withYear(newYear)

                            if (newDate.compareTo(minDate) >= 0 && newDate.compareTo(maxDate) <= 0) {
                                snappedDate = newDate
                            }

                            dayOfMonths =
                                calculateDayOfMonths(snappedDate.month.number, snappedDate.year)

                            val newIndex = years.find { it.value == snappedDate.year }?.index

                            newIndex?.let {
                                onSnappedDate(
                                    SnappedDate.Year(
                                        localDate = snappedDate,
                                        index = newIndex
                                    )
                                )?.let { return@MyFinanceWheelTextPicker it }

                            }
                        }

                        return@MyFinanceWheelTextPicker years.find { it.value == snappedDate.year }?.index
                    }
                )
            }
        }
    }
}