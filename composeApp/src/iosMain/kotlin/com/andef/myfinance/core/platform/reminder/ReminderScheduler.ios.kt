package com.andef.myfinance.core.platform.reminder

import com.andef.myfinance.core.domain.reminder.entities.ReminderRepeatType
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import kotlinx.datetime.plus
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.Foundation.timeIntervalSince1970
import platform.UserNotifications.UNCalendarNotificationTrigger
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationSound
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter
import kotlin.time.ExperimentalTime

class IosReminderScheduler : ReminderScheduler {
    @OptIn(ExperimentalTime::class)
    override fun schedule(
        id: Long,
        text: String,
        date: LocalDate,
        time: LocalTime,
        repeatType: ReminderRepeatType?
    ) {
        val content = UNMutableNotificationContent().apply {
            setTitle("Мои финансы")
            setBody(text)
            setSound(UNNotificationSound.defaultSound())
        }

        if (repeatType == null) {
            addOneTimeRequest(id.toString(), content, date, time)
            return
        }

        val needsSeparateInitialRequest = when (repeatType) {
            ReminderRepeatType.FIRST_DAY_OF_MONTH -> date.day != 1
            ReminderRepeatType.LAST_DAY_OF_MONTH -> date.day != lastDayOfMonth(date)
            else -> false
        }
        if (needsSeparateInitialRequest) {
            addOneTimeRequest(id.toString(), content, date, time)
        }

        repeatDateComponents(date, time, repeatType).forEachIndexed { index, components ->
            val trigger = UNCalendarNotificationTrigger.triggerWithDateMatchingComponents(
                dateComponents = components,
                repeats = true
            )
            addRequest(repeatIdentifier(id, index), content, trigger)
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun addOneTimeRequest(
        identifier: String,
        content: UNMutableNotificationContent,
        date: LocalDate,
        time: LocalTime
    ) {
        val triggerAtMillis = LocalDateTime(
            date.year,
            date.month,
            date.day,
            time.hour,
            time.minute
        ).toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
        val intervalSeconds = (triggerAtMillis / 1000.0) - NSDate().timeIntervalSince1970
        if (intervalSeconds <= 0) return
        val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(
            timeInterval = intervalSeconds,
            repeats = false
        )
        addRequest(identifier, content, trigger)
    }

    private fun addRequest(
        identifier: String,
        content: UNMutableNotificationContent,
        trigger: platform.UserNotifications.UNNotificationTrigger
    ) {
        val request = UNNotificationRequest.requestWithIdentifier(identifier, content, trigger)
        UNUserNotificationCenter.currentNotificationCenter().addNotificationRequest(
            request
        ) { error -> }
    }

    private fun repeatDateComponents(
        date: LocalDate,
        time: LocalTime,
        repeatType: ReminderRepeatType
    ): List<NSDateComponents> {
        fun components(month: Int? = null, day: Int? = null, weekday: Int? = null) =
            NSDateComponents().apply {
                month?.let { this.month = it.toLong() }
                day?.let { this.day = it.toLong() }
                weekday?.let { this.weekday = it.toLong() }
                hour = time.hour.toLong()
                minute = time.minute.toLong()
            }

        return when (repeatType) {
            ReminderRepeatType.WEEKLY -> listOf(
                components(weekday = (date.dayOfWeek.ordinal + 1) % 7 + 1)
            )

            ReminderRepeatType.MONTHLY -> (1..12).map { month ->
                components(month = month, day = minOf(date.day, lastDayOfMonth(month)))
            }

            ReminderRepeatType.FIRST_DAY_OF_MONTH -> listOf(components(day = 1))

            ReminderRepeatType.LAST_DAY_OF_MONTH -> (1..12).map { month ->
                components(month = month, day = lastDayOfMonth(month))
            }

            ReminderRepeatType.EVERY_THREE_MONTHS -> (0..3).map { index ->
                val month = (date.month.number - 1 + index * 3) % 12 + 1
                components(month = month, day = minOf(date.day, lastDayOfMonth(month)))
            }

            ReminderRepeatType.YEARLY -> listOf(
                components(month = date.month.number, day = date.day)
            )
        }
    }

    private fun lastDayOfMonth(month: Int): Int {
        val firstDay = LocalDate(2001, month, 1)
        return firstDay.plus(DatePeriod(months = 1, days = -1)).day
    }

    private fun lastDayOfMonth(date: LocalDate): Int {
        val firstDay = LocalDate(date.year, date.month, 1)
        return firstDay.plus(DatePeriod(months = 1, days = -1)).day
    }

    override fun cancel(id: Long) {
        val identifiers = listOf(id.toString()) + (0..11).map { repeatIdentifier(id, it) }
        UNUserNotificationCenter.currentNotificationCenter()
            .removePendingNotificationRequestsWithIdentifiers(identifiers)
        UNUserNotificationCenter.currentNotificationCenter()
            .removeDeliveredNotificationsWithIdentifiers(identifiers)
    }

    private fun repeatIdentifier(id: Long, index: Int): String = "${id}_repeat_$index"
}
