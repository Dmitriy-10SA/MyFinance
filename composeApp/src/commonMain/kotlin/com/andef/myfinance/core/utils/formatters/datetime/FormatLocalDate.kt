package com.andef.myfinance.core.utils.formatters.datetime

import com.andef.myfinance.core.utils.getters.minusDays
import com.andef.myfinance.core.utils.getters.now
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
    val today = LocalDate.now()
    return "${date.day.toString().padStart(2, '0')}." +
            "${date.month.number.toString().padStart(2, '0')}." +
            "${date.year}"
}