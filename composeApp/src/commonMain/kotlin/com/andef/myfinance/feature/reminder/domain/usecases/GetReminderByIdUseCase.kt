package com.andef.myfinance.feature.reminder.domain.usecases

import com.andef.myfinance.core.domain.reminder.entities.ReminderModel
import com.andef.myfinance.core.domain.reminder.repository.ReminderRepository

class GetReminderByIdUseCase(private val repository: ReminderRepository) {
    suspend operator fun invoke(id: Long): ReminderModel = repository.getReminderById(id)
}