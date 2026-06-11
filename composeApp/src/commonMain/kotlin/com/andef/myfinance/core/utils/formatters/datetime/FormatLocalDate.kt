package com.andef.myfinance.core.utils.formatters.datetime

import com.andef.myfinance.core.utils.getters.now
import com.kizitonwose.calendar.core.minusDays
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

    val startOfCurrentWeek = today.minusDays(today.dayOfWeek.ordinal)

    val startOfCurrentMonth = LocalDate(year = today.year, month = today.month.number, day = 1)

    val startOfCurrentYear = LocalDate(year = today.year, month = 1, day = 1)

    return when (startDate) {
        endDate -> formatLocalDate(startDate)
        startOfCurrentWeek if endDate == today -> {
            "Текущая неделя"
        }

        startOfCurrentMonth if endDate == today -> {
            getMonthName(today.month.number)
        }

        startOfCurrentYear if endDate == today -> {
            today.year.toString()
        }

        else -> {
            "${formatLocalDateForPrint(startDate)} - ${formatLocalDateForPrint(endDate)}"
        }
    }
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