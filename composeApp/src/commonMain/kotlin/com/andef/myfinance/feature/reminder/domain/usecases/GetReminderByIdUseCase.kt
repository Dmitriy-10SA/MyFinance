package com.andef.myfinance.feature.reminder.domain.usecases

import com.andef.myfinance.core.domain.reminder.entities.Reminder
import com.andef.myfinance.core.domain.reminder.repository.ReminderRepository

class GetReminderByIdUseCase(private val repository: ReminderRepository) {
    suspend operator fun invoke(id: Long): Reminder = repository.getReminderById(id)
}