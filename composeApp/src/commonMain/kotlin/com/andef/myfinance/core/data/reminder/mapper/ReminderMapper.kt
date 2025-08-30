package com.andef.myfinance.core.data.reminder.mapper

import com.andef.myfinance.core.domain.reminder.entities.ReminderModel
import com.andef.myfinance.core.utils.mappers.localdate.intToLocalDate
import com.andef.myfinance.core.utils.mappers.localdate.localDateToInt
import com.andef.myfinance.core.utils.mappers.localtime.intToLocalTime
import com.andef.myfinance.core.utils.mappers.localtime.localTimeToInt
import com.andef.myfinance.db.Reminder

class ReminderMapper {
    fun toEntity(dbo: Reminder): ReminderModel = ReminderModel(
        id = dbo.id,
        text = dbo.text,
        date = intToLocalDate(dbo.date.toInt()),
        time = intToLocalTime(dbo.time.toInt())
    )

    fun fromEntity(entity: ReminderModel): Reminder = Reminder(
        id = entity.id,
        text = entity.text,
        date = localDateToInt(entity.date).toLong(),
        time = localTimeToInt(entity.time).toLong()
    )
}