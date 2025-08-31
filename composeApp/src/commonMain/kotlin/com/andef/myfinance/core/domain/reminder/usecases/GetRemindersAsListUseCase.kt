package com.andef.myfinance.core.domain.reminder.usecases

import com.andef.myfinance.core.domain.reminder.repository.ReminderRepository
import kotlinx.datetime.LocalDate

class GetRemindersAsListUseCase(private val repository: ReminderRepository) {
    suspend operator fun invoke(startDate: LocalDate, endDate: LocalDate) =
        repository.getRemindersAsList(startDate, endDate)
}