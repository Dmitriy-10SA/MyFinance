package com.andef.myfinance.core.domain.reminder.usecases

import com.andef.myfinance.core.domain.reminder.entities.ReminderModel
import com.andef.myfinance.core.domain.reminder.repository.ReminderRepository

class AddReminderUseCase(private val repository: ReminderRepository) {
    suspend operator fun invoke(reminderModel: ReminderModel): Long = repository.addReminder(reminderModel)
}