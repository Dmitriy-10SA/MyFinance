package com.andef.myfinance.core.domain.reminder.usecases

import com.andef.myfinance.core.domain.reminder.entities.Reminder
import com.andef.myfinance.core.domain.reminder.repository.ReminderRepository

class AddReminderUseCase(private val repository: ReminderRepository) {
    suspend operator fun invoke(reminder: Reminder): Long = repository.addReminder(reminder)
}