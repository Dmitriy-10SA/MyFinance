package com.andef.myfinance.core.domain.reminder.usecases

import com.andef.myfinance.core.domain.reminder.entities.ReminderModel
import com.andef.myfinance.core.domain.reminder.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

class GetRemindersUseCase(private val repository: ReminderRepository) {
    operator fun invoke(startDate: LocalDate, endDate: LocalDate): Flow<List<ReminderModel>> =
        repository.getReminders(startDate, endDate)
}