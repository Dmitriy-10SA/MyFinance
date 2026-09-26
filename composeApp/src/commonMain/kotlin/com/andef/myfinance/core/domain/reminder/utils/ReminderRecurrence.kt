package com.andef.myfinance.core.domain.reminder.utils

import com.andef.myfinance.core.domain.reminder.entities.ReminderModel
import com.andef.myfinance.core.domain.reminder.entities.ReminderRepeatType
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

fun ReminderModel.occurrencesBetween(
    startDate: LocalDate,
    endDate: LocalDate
): List<ReminderModel> {
    if (date > endDate || startDate > endDate) return emptyList()
    if (repeatType == null) return if (date >= startDate) listOf(this) else emptyList()

    val occurrences = mutableListOf<ReminderModel>()
    var occurrenceIndex = 0
    var occurrenceDate = occurrenceDateAt(date, repeatType, occurrenceIndex)
    while (occurrenceDate <= endDate) {
        if (occurrenceDate >= startDate) occurrences += copy(date = occurrenceDate)
        occurrenceIndex++
        occurrenceDate = occurrenceDateAt(date, repeatType, occurrenceIndex)
    }
    return occurrences
}

fun nextOccurrenceDate(
    date: LocalDate,
    repeatType: ReminderRepeatType?,
    fromDate: LocalDate
): LocalDate? {
    if (repeatType == null) return date.takeIf { it >= fromDate }

    var occurrenceIndex = 0
    var occurrenceDate = occurrenceDateAt(date, repeatType, occurrenceIndex)
    while (occurrenceDate < fromDate) {
        occurrenceIndex++
        occurrenceDate = occurrenceDateAt(date, repeatType, occurrenceIndex)
    }
    return occurrenceDate
}

private fun occurrenceDateAt(
    startDate: LocalDate,
    repeatType: ReminderRepeatType,
    occurrenceIndex: Int
): LocalDate {
    if (occurrenceIndex == 0) return startDate

    return when (repeatType) {
        ReminderRepeatType.WEEKLY -> startDate.plus(DatePeriod(days = occurrenceIndex * 7))
        ReminderRepeatType.MONTHLY -> dateInShiftedMonth(startDate, occurrenceIndex)
        ReminderRepeatType.FIRST_DAY_OF_MONTH -> {
            val startMonth = LocalDate(startDate.year, startDate.month, 1)
            startMonth.plus(DatePeriod(months = occurrenceIndex))
        }

        ReminderRepeatType.LAST_DAY_OF_MONTH -> {
            val startMonth = LocalDate(startDate.year, startDate.month, 1)
            val startMonthLastDay = startMonth.plus(DatePeriod(months = 1, days = -1))
            val monthOffset = if (startDate < startMonthLastDay) {
                occurrenceIndex - 1
            } else {
                occurrenceIndex
            }
            val month = startMonth.plus(DatePeriod(months = monthOffset))
            month.plus(DatePeriod(months = 1, days = -1))
        }

        ReminderRepeatType.EVERY_THREE_MONTHS -> {
            dateInShiftedMonth(startDate, occurrenceIndex * 3)
        }

        ReminderRepeatType.YEARLY -> dateInShiftedYear(startDate, occurrenceIndex)
    }
}

private fun dateInShiftedMonth(startDate: LocalDate, months: Int): LocalDate {
    val month = LocalDate(startDate.year, startDate.month, 1)
        .plus(DatePeriod(months = months))
    val lastDay = month.plus(DatePeriod(months = 1, days = -1)).day
    return LocalDate(month.year, month.month, minOf(startDate.day, lastDay))
}

private fun dateInShiftedYear(startDate: LocalDate, years: Int): LocalDate {
    val year = startDate.year + years
    val month = LocalDate(year, startDate.month, 1)
    val lastDay = month.plus(DatePeriod(months = 1, days = -1)).day
    return LocalDate(year, startDate.month, minOf(startDate.day, lastDay))
}
