package com.andef.myfinance.feature.reminder_common.reminder_all.presentation

import com.andef.myfinance.core.domain.reminder.entities.ReminderModel
import com.andef.myfinance.core.utils.getters.now
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class AllRemindersState(
    val reminders: List<ReminderModel> = emptyList(),
    val remindersForScreenAsList: List<ReminderModel> = emptyList(),
    val remindersLocalDatesForScreenAsSet: Set<LocalDate> = emptySet(),
    val reminderTextInBottomSheet: String? = null,
    val reminderIdInBottomSheet: Long? = null,
    val reminderDateInBottomSheet: LocalDate? = null,
    val reminderTimeInBottomSheet: LocalTime? = null,
    val reminderSheetVisible: Boolean = false,
    val currentDate: LocalDate = LocalDate.now(),
    val deleteDialogVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)