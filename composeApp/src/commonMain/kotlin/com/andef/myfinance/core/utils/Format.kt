package com.andef.myfinance.core.utils

import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.math.pow
import kotlin.math.round
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

fun Float.format(digits: Int = 2): String {
    val factor = 10.0.pow(digits)
    val rounded = round(this * factor) / factor
    return buildString {
        append(rounded.toString())
        val dotIndex = indexOf('.')
        if (dotIndex == -1) {
            append('.')
            repeat(digits) { append('0') }
        } else {
            val decimals = length - dotIndex - 1
            repeat(digits - decimals) { append('0') }
        }
    }
}

fun formatPriceRuble(value: Double): String {
    val rounded = (round(value * 100) / 100)
    val parts = rounded.toString().split(".")
    val intPart = parts[0]
    val fracPart = parts.getOrElse(1) { "0" }.padEnd(2, '0').take(2)

    val groupedInt = intPart.reversed()
        .chunked(3)
        .joinToString(" ")
        .reversed()

    return "$groupedInt.$fracPart₽"
}

fun formatAmountForEdit(value: Double): String {
    val rounded = (round(value * 100) / 100)
    val parts = rounded.toString().split(".")
    val intPart = parts[0]
    val fracPart = parts.getOrElse(1) { "0" }.padEnd(2, '0').take(2)

    return "$intPart,$fracPart"
}

fun clampToTwoDecimals(input: String): String {
    val idx = input.indexOfFirst { it == '.' || it == ',' }
    return if (idx >= 0 && input.length > idx + 3) {
        input.substring(0, idx + 3)
    } else {
        input
    }
}

fun LocalTime.toInt(): Int = hour * 100 + minute

fun Int.toLocalTime(): LocalTime =
    LocalTime(hour = this / 100, minute = this % 100)

fun formatLocalTime(time: LocalTime): String =
    "${time.hour.toString().padStart(2, '0')}:${time.minute.toString().padStart(2, '0')}"

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
fun epochMillisToLocalDate(monthMillis: Long): LocalDate {
    val instant = Instant.fromEpochMilliseconds(monthMillis)
    val tz = TimeZone.currentSystemDefault()
    return instant.toLocalDateTime(tz).date
}

private fun LocalDate.formatAsDate(): String =
    "${day.toString().padStart(2, '0')}.${month.number.toString().padStart(2, '0')}.$year"

private fun LocalDate.formatAsMonthYear(): String {
    val monthsRu = listOf(
        "январь", "февраль", "март", "апрель", "май", "июнь",
        "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"
    )
    return "${monthsRu[month.number - 1]} $year"
}

fun LocalDate.toInt(): Int = localDateToInt(this)
fun Int.toLocalDate(): LocalDate = intToLocalDate(this)

private fun localDateToInt(date: LocalDate): Int =
    date.year * 10000 + date.month.number * 100 + date.day

private fun intToLocalDate(value: Int): LocalDate =
    LocalDate(
        year = value / 10000,
        month = (value % 10000) / 100,
        day = value % 100
    )

@OptIn(ExperimentalTime::class)
fun formatLocalDate(date: LocalDate): String {
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val yesterday = today.minus(DatePeriod(days = 1))

    return when (date) {
        today -> "Сегодня"
        yesterday -> "Вчера"
        else -> "${date.day.toString().padStart(2, '0')}." +
                "${date.month.number.toString().padStart(2, '0')}." +
                "${date.year}"
    }
}