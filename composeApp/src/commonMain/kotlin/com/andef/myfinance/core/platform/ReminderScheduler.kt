package com.andef.myfinance.core.platform

interface ReminderScheduler {
    fun schedule(id: Long, text: String, triggerAtMillis: Long)
    fun cancel(id: Long)
}