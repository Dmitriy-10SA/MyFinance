package com.andef.myfinance.core.domain.reminder.usecases

import com.andef.myfinance.core.domain.reminder.entities.Reminder
import com.andef.myfinance.core.domain.reminder.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

class GetRemindersUseCase(private val repository: ReminderRepository) {
    operator fun invoke(startDate: LocalDate, endDate: LocalDate): Flow<List<Reminder>> =
        repository.getReminders(startDate, endDate)
}