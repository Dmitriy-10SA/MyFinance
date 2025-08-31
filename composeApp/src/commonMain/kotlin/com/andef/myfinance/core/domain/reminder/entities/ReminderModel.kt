package com.andef.myfinance.core.domain.reminder.entities

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReminderModel(
    @SerialName("id")
    val id: Long,
    @SerialName("text")
    val text: String,
    @SerialName("date")
    val date: LocalDate,
    @SerialName("time")
    val time: LocalTime
)