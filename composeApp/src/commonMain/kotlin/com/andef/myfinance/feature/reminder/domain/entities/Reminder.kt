package com.andef.myfinance.feature.reminder.domain.entities

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class Reminder(
    val id: Long,
    val text: String,
    val date: LocalDate,
    val time: LocalTime
)