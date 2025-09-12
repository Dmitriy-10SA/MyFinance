package com.andef.myfinance.core.utils.formatters.datetime

import kotlinx.datetime.LocalTime

fun formatLocalTime(time: LocalTime): String =
    "${time.hour.toString().padStart(2, '0')}:${time.minute.toString().padStart(2, '0')}"
