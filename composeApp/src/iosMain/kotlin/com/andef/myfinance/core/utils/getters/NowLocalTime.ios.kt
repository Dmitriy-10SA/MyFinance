package com.andef.myfinance.core.utils.getters

import kotlinx.datetime.LocalTime
import platform.Foundation.NSCalendar
import platform.Foundation.NSCalendarUnitHour
import platform.Foundation.NSCalendarUnitMinute
import platform.Foundation.NSCalendarUnitSecond
import platform.Foundation.NSDate
import platform.Foundation.now

actual fun LocalTime.Companion.now(): LocalTime {
    val date = NSDate.now()
    val calendar = NSCalendar.currentCalendar
    val components = calendar.components(
        unitFlags = NSCalendarUnitHour or NSCalendarUnitMinute or NSCalendarUnitSecond,
        fromDate = date
    )
    val hour = components.hour.toInt()
    val minute = components.minute.toInt()
    return LocalTime(hour, minute)
}