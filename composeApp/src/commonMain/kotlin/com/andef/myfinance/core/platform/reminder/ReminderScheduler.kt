package com.andef.myfinance.core.platform.reminder

import com.andef.myfinance.core.domain.reminder.entities.ReminderRepeatType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

interface ReminderScheduler {
    fun schedule(
        id: Long,
        text: String,
        date: LocalDate,
        time: LocalTime,
        repeatType: ReminderRepeatType?
    )

    fun cancel(id: Long)
}
