package com.andef.myfinance.core.platform

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNNotificationSound
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter


class IosReminderScheduler : ReminderScheduler {
    override fun schedule(id: Long, text: String, triggerAtMillis: Long) {
        val content = UNMutableNotificationContent().apply {
            setTitle("Мои финансы")
            setBody(text)
            setSound(UNNotificationSound.defaultSound())
        }

        val intervalSeconds = (triggerAtMillis / 1000.0) - NSDate().timeIntervalSince1970
        if (intervalSeconds <= 0) {
            return
        }

        val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(
            timeInterval = intervalSeconds,
            repeats = false
        )

        val request = UNNotificationRequest.requestWithIdentifier(
            identifier = id.toString(),
            content = content,
            trigger = trigger
        )

        UNUserNotificationCenter.currentNotificationCenter().addNotificationRequest(
            request
        ) { error -> }
    }

    override fun cancel(id: Long) {
        UNUserNotificationCenter.currentNotificationCenter()
            .removePendingNotificationRequestsWithIdentifiers(listOf(id.toString()))
        UNUserNotificationCenter.currentNotificationCenter()
            .removeDeliveredNotificationsWithIdentifiers(listOf(id.toString()))
    }
}