package com.andef.myfinance.core.domain.reminder.repository

import com.andef.myfinance.core.domain.reminder.entities.ReminderModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

interface ReminderRepository {
    suspend fun getReminderById(id: Long): ReminderModel
    suspend fun addReminder(reminderModel: ReminderModel): Long
    suspend fun changeReminder(id: Long, text: String, date: LocalDate, time: LocalTime)
    suspend fun deleteReminder(id: Long)
    fun getReminders(startDate: LocalDate, endDate: LocalDate): Flow<List<ReminderModel>>
    suspend fun getRemindersAsList(startDate: LocalDate, endDate: LocalDate): List<ReminderModel>
}