package com.andef.myfinance.core.utils.date

import com.andef.myfinance.core.utils.getters.minusDays
import com.andef.myfinance.core.utils.getters.plusDays
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import kotlinx.datetime.plus

fun currentDateRangeForTab(tabId: Int, now: LocalDate): Pair<LocalDate, LocalDate> {
    return when (tabId) {
        0 -> now to now
        1 -> {
            val startOfWeek = now.minusDays(now.dayOfWeek.ordinal.toLong())
            startOfWeek to startOfWeek.plusDays(6)
        }

        2 -> selectedMonthRange(now.year, now.month.number)
        3 -> selectedYearRange(now.year)
        else -> now to now
    }
}

fun selectedMonthRange(year: Int, month: Int): Pair<LocalDate, LocalDate> {
    val startOfMonth = LocalDate(year = year, month = month, day = 1)
    return startOfMonth to startOfMonth.plus(DatePeriod(months = 1, days = -1))
}

fun selectedYearRange(year: Int): Pair<LocalDate, LocalDate> {
    return LocalDate(year = year, month = 1, day = 1) to
            LocalDate(year = year, month = 12, day = 31)
}
