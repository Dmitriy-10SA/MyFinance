package com.andef.myfinance.core.domain.reminder.usecases

import com.andef.myfinance.core.domain.reminder.repository.ReminderRepository

class DeleteReminderUseCase(private val repository: ReminderRepository) {
    suspend operator fun invoke(id: Long) = repository.deleteReminder(id)
}