package com.andef.myfinance.core.data.reminder.repository

import com.andef.myfinance.core.data.reminder.dao.ReminderDao
import com.andef.myfinance.core.data.reminder.mapper.ReminderMapper
import com.andef.myfinance.core.domain.reminder.entities.ReminderModel
import com.andef.myfinance.core.domain.reminder.repository.ReminderRepository
import com.andef.myfinance.core.platform.reminder.ReminderScheduler
import com.andef.myfinance.core.utils.mappers.localdate.localDateToInt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.ExperimentalTime

class ReminderRepositoryImpl(
    private val dao: ReminderDao,
    private val reminderScheduler: ReminderScheduler,
    private val mapper: ReminderMapper
) : ReminderRepository {
    override suspend fun getReminderById(id: Long): ReminderModel {
        return mapper.toEntity(dao.getReminderById(id))
    }

    override suspend fun addReminder(reminderModel: ReminderModel) {
        val id = dao.insert(mapper.fromEntity(reminderModel))
        reminderScheduler.schedule(
            id,
            reminderModel.text,
            toEpochMillis(reminderModel.date, reminderModel.time)
        )
    }

    override suspend fun changeReminder(
        id: Long,
        text: String,
        date: LocalDate,
        time: LocalTime
    ) {
        val entity = ReminderModel(id, text, date, time)
        dao.update(mapper.fromEntity(entity))
        reminderScheduler.cancel(id)
        reminderScheduler.schedule(id, text, toEpochMillis(date, time))
    }

    override suspend fun deleteReminder(id: Long) {
        dao.deleteById(id)
        reminderScheduler.cancel(id)
    }

    override fun getReminders(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<ReminderModel>> {
        return dao.getRemindersBetween(localDateToInt(startDate), localDateToInt(endDate))
            .map { list -> list.map { mapper.toEntity(it) } }
    }

    override suspend fun getRemindersAsList(
        startDate: LocalDate,
        endDate: LocalDate
    ): List<ReminderModel> {
        return dao.getRemindersAsList(localDateToInt(startDate), localDateToInt(endDate))
            .map { mapper.toEntity(it) }
    }

    @OptIn(ExperimentalTime::class)
    private fun toEpochMillis(date: LocalDate, time: LocalTime): Long {
        val dateTime = LocalDateTime(
            date.year,
            date.month,
            date.day,
            time.hour,
            time.minute
        )
        val instant = dateTime
            .toInstant(TimeZone.currentSystemDefault())
        return instant.toEpochMilliseconds()
    }
}