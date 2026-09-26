package com.andef.myfinance.core.platform.reminder

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.content.ContextCompat
import com.andef.myfinance.ReminderReceiver
import com.andef.myfinance.core.domain.reminder.entities.ReminderRepeatType
import com.andef.myfinance.core.domain.reminder.utils.nextOccurrenceDate
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.number
import kotlinx.datetime.plus
import java.time.ZoneId
import java.time.ZonedDateTime

class AndroidReminderScheduler(private val context: Context) : ReminderScheduler {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun schedule(
        id: Long,
        text: String,
        date: LocalDate,
        time: LocalTime,
        repeatType: ReminderRepeatType?
    ) {
        val zoneId = ZoneId.systemDefault()
        val now = ZonedDateTime.now(zoneId)
        val today = LocalDate(now.year, now.monthValue, now.dayOfMonth)
        val fromDate = if (
            time.hour > now.hour || time.hour == now.hour && time.minute > now.minute
        ) {
            today
        } else {
            today.plus(DatePeriod(days = 1))
        }
        val triggerDate = nextOccurrenceDate(date, repeatType, fromDate) ?: return
        val triggerAtMillis = ZonedDateTime.of(
            triggerDate.year,
            triggerDate.month.number,
            triggerDate.day,
            time.hour,
            time.minute,
            0,
            0,
            zoneId
        ).toInstant().toEpochMilli()
        val intent = ReminderReceiver.newIntent(
            context = context,
            id = id.toInt(),
            text = text,
            repeatType = repeatType,
            startDate = date,
            hour = time.hour,
            minute = time.minute
        )
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )
    }

    override fun cancel(id: Long) {
        val intent = ReminderReceiver.newIntent(context, id.toInt(), "")
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()

        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.cancel(id.toInt())
    }

}
