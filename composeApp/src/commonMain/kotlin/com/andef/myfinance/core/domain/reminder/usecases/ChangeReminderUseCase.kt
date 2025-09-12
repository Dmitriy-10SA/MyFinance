package com.andef.myfinance.core.domain.reminder.usecases

import com.andef.myfinance.core.domain.reminder.repository.ReminderRepository
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class ChangeReminderUseCase(private val repository: ReminderRepository) {
    suspend operator fun invoke(id: Long, text: String, date: LocalDate, time: LocalTime) =
        repository.changeReminder(id, text, date, time)
}