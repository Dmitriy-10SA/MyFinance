package com.andef.myfinance.feature.reminder_common.reminder_all.presentation

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

sealed class AllRemindersIntent {
    data object SubscribeToReminders : AllRemindersIntent()

    data class DateSelected(val date: LocalDate) : AllRemindersIntent()
    data class DeleteDialogVisibleChange(val isVisible: Boolean) : AllRemindersIntent()
    data class DeleteReminder(val id: Long, val onError: (String) -> Unit) : AllRemindersIntent()
    data class ReminderBottomSheetVisibleChange(
        val isVisible: Boolean,
        val reminderId: Long? = null,
        val reminderText: String? = null,
        val reminderDate: LocalDate? = null,
        val reminderTime: LocalTime? = null
    ) : AllRemindersIntent()
}