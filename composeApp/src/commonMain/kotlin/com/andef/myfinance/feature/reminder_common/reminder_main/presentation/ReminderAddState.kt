package com.andef.myfinance.feature.reminder_common.reminder_main.presentation

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class ReminderAddState(
    val reminderId: Long? = null,
    val reminderText: String = "",
    val reminderDate: LocalDate? = null,
    val reminderTime: LocalTime? = null,
    val isLoading: Boolean = false,
    val datePickerVisible: Boolean = false,
    val timePickerVisible: Boolean = false,
    val saveButtonEnabled: Boolean = false,
    val isAdd: Boolean = true
)