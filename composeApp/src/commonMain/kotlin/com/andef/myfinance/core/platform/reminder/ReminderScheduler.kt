package com.andef.myfinance.core.platform.reminder

interface ReminderScheduler {
    fun schedule(id: Long, text: String, triggerAtMillis: Long)
    fun cancel(id: Long)
}