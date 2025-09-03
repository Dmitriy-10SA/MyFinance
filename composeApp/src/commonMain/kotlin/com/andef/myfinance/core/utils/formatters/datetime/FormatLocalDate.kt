package com.andef.myfinance.core.utils.formatters.datetime

import com.kizitonwose.calendar.core.minusDays
import com.kizitonwose.calendar.core.now
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
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