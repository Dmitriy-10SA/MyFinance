package com.andef.myfinance.core.data.reminder.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.andef.myfinance.db.Reminder
import com.andef.myfinance.db.ReminderQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class ReminderDao(private val queries: ReminderQueries) {
    fun getReminderById(id: Long): Reminder = queries.getReminderById(id).executeAsOne()

    fun insert(reminder: Reminder) = queries.insertReminder(
        reminder.text,
        reminder.date,
        reminder.time
    )

    fun update(reminder: Reminder) = queries.updateReminder(
        reminder.text,
        reminder.date,
        reminder.time,
        reminder.id
    )

    fun deleteById(id: Long) = queries.deleteReminderById(id)

    fun getRemindersBetween(startDate: Int, endDate: Int): Flow<List<Reminder>> =
        queries.getRemindersBetween(startDate.toLong(), endDate.toLong())
            .asFlow()
            .flowOn(Dispatchers.IO)
            .mapToList(Dispatchers.IO)

    fun getRemindersAsList(startDate: Int, endDate: Int): List<Reminder> =
        queries.getRemindersBetween(startDate.toLong(), endDate.toLong())
            .executeAsList()
}