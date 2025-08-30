package com.andef.myfinance.core.utils.formatters.datetime

import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
val russianDateFormatter = object : DatePickerFormatter {
    override fun formatDate(
        dateMillis: Long?,
        locale: CalendarLocale,
        forContentDescription: Boolean
    ): String? {
        if (dateMillis == null) return null
        val date = epochMillisToLocalDate(dateMillis)
        return date.formatAsDate()
    }

    override fun formatMonthYear(
        monthMillis: Long?,
        locale: CalendarLocale
    ): String? {
        if (monthMillis == null) return null
        val date = epochMillisToLocalDate(monthMillis)
        return date.formatAsMonthYear()
    }
}

@OptIn(ExperimentalTime::class)
private fun epochMillisToLocalDate(monthMillis: Long): LocalDate {
    val instant = Instant.fromEpochMilliseconds(monthMillis)
    val tz = TimeZone.currentSystemDefault()
    return instant.toLocalDateTime(tz).date
}

private fun LocalDate.formatAsDate(): String =
    "${day.toString().padStart(2, '0')}.${month.number.toString().padStart(2, '0')}.$year"

private fun LocalDate.formatAsMonthYear(): String {
    val monthsRu = listOf(
        "января", "февраля", "марта", "апреля", "мая", "июня",
        "июля", "августа", "сентября", "октября", "ноября", "декабря"
    )
    return "${monthsRu[month.number - 1]} $year"
}