package com.andef.myfinance.core.utils.formatters.datetime

import com.andef.myfinance.core.utils.date.selectedMonthRange
import com.andef.myfinance.core.utils.getters.minusDays
import com.andef.myfinance.core.utils.getters.now
import com.andef.myfinance.core.utils.getters.plusDays
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number

fun formatLocalDate(date: LocalDate): String {
    val today = LocalDate.now()
    val yesterday = today.minusDays(1)

    return when (date) {
        today -> "Сегодня"
        yesterday -> "Вчера"
        else -> "${date.day.toString().padStart(2, '0')}." +
                "${date.month.number.toString().padStart(2, '0')}." +
                "${date.year}"
    }
}

fun formatLocalDateForPrint(date: LocalDate): String {
    return "${date.day.toString().padStart(2, '0')}." +
            "${date.month.number.toString().padStart(2, '0')}." +
            "${date.year}"
}

fun formatLocalDateRange(startDate: LocalDate, endDate: LocalDate): String {
    val today = LocalDate.now()

    val startOfCurrentWeek = today.minusDays(today.dayOfWeek.ordinal.toLong())

    val endOfCurrentWeek = startOfCurrentWeek.plusDays(6)

    return when {
        startDate == endDate -> formatLocalDate(startDate)
        startDate == startOfCurrentWeek && endDate == endOfCurrentWeek -> "Текущая неделя"

        isFullMonthRange(startDate, endDate) -> {
            getMonthName(startDate.month.number) + " " + startDate.year.toString()
        }

        isFullYearRange(startDate, endDate) -> startDate.year.toString()

        else -> "${formatLocalDateForPrint(startDate)} - ${formatLocalDateForPrint(endDate)}"
    }
}

private fun isFullMonthRange(startDate: LocalDate, endDate: LocalDate): Boolean {
    val startOfMonth = LocalDate(
        year = startDate.year,
        month = startDate.month.number,
        day = 1
    )

    val endOfMonth = selectedMonthRange(
        year = startDate.year,
        month = startDate.month.number
    ).second

    return startDate == startOfMonth && endDate == endOfMonth
}

private fun isFullYearRange(startDate: LocalDate, endDate: LocalDate): Boolean {
    val startOfYear = LocalDate(
        year = startDate.year,
        month = 1,
        day = 1
    )

    val endOfYear = LocalDate(
        year = startDate.year,
        month = 12,
        day = 31
    )

    return startDate == startOfYear && endDate == endOfYear
}

private fun getMonthName(monthNumber: Int): String {
    return when (monthNumber) {
        1 -> "Январь"
        2 -> "Февраль"
        3 -> "Март"
        4 -> "Апрель"
        5 -> "Май"
        6 -> "Июнь"
        7 -> "Июль"
        8 -> "Август"
        9 -> "Сентябрь"
        10 -> "Октябрь"
        11 -> "Ноябрь"
        12 -> "Декабрь"
        else -> ""
    }
}