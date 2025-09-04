package com.andef.myfinance.feature.reminder_common.reminder_all.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.core.domain.reminder.usecases.DeleteReminderUseCase
import com.andef.myfinance.core.domain.reminder.usecases.GetRemindersUseCase
import com.andef.myfinance.core.utils.getters.minusDays
import com.andef.myfinance.core.utils.getters.now
import com.andef.myfinance.core.utils.getters.plusDays
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

class AllRemindersViewModel(
    private val deleteReminderUseCase: DeleteReminderUseCase,
    private val getRemindersUseCase: GetRemindersUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AllRemindersState())
    val state: StateFlow<AllRemindersState> = _state

    fun send(intent: AllRemindersIntent) {
        when (intent) {
            is AllRemindersIntent.DateSelected -> {
                dateSelected(intent.date)
            }

            is AllRemindersIntent.DeleteDialogVisibleChange -> {
                _state.value = _state.value.copy(deleteDialogVisible = intent.isVisible)
            }

            is AllRemindersIntent.DeleteReminder -> {
                deleteReminder(intent.id, intent.onError)
            }

            is AllRemindersIntent.ReminderBottomSheetVisibleChange -> {
                _state.value = _state.value.copy(
                    reminderSheetVisible = intent.isVisible,
                    reminderIdInBottomSheet = intent.reminderId,
                    reminderTextInBottomSheet = intent.reminderText,
                    reminderDateInBottomSheet = intent.reminderDate,
                    reminderTimeInBottomSheet = intent.reminderTime
                )
            }

            AllRemindersIntent.SubscribeToReminders -> {
                subscribeToReminders()
            }
        }
    }

    private fun getFirstMondayInWeekOfDate(date: LocalDate): LocalDate {
        var outDate = date
        while (outDate.dayOfWeek.name != DayOfWeek.MONDAY.name) {
            outDate = outDate.minusDays(1)
        }
        return outDate
    }

    private fun getLastSundayInWeekOfDate(date: LocalDate): LocalDate {
        var outDate = date
        while (outDate.dayOfWeek.name != DayOfWeek.SUNDAY.name) {
            outDate = outDate.plusDays(1)
        }
        return outDate
    }

    private var isFirstLaunch: Boolean = true
    private var job: Job? = null
    private fun subscribeToReminders() {
        if (isFirstLaunch == true || state.value.isError) {
            isFirstLaunch = false
            job?.cancel()
            job = viewModelScope.launch {
                val today = LocalDate.now()
                val previousMonday = getFirstMondayInWeekOfDate(today)
                val endSunday = getLastSundayInWeekOfDate(today)
                getRemindersUseCase.invoke(previousMonday, endSunday)
                    .onStart {
                        _state.value = _state.value.copy(isLoading = true, isError = false)
                    }
                    .catch {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            isError = true,
                            reminders = listOf(),
                            remindersForScreenAsList = listOf(),
                            remindersLocalDatesForScreenAsSet = setOf()
                        )
                    }
                    .collect { reminders ->
                        val remindersForScreenAsList = withContext(Dispatchers.IO) {
                            reminders.filter { it.date == _state.value.currentDate }
                        }
                        val remindersLocalDatesForScreenAsSet = withContext(Dispatchers.IO) {
                            reminders.map { it.date }.toSet()
                        }
                        _state.value = _state.value.copy(
                            isLoading = false,
                            remindersForScreenAsList = remindersForScreenAsList,
                            remindersLocalDatesForScreenAsSet = remindersLocalDatesForScreenAsSet,
                            reminders = reminders,
                            isError = false
                        )
                    }
            }
        }
    }

    private fun deleteReminder(id: Long, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                withContext(Dispatchers.IO) { deleteReminderUseCase.invoke(id) }
            } catch (_: Exception) {
                onError("Ошибка! Попробуйте ещё раз!")
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    private fun dateSelected(date: LocalDate) {
        viewModelScope.launch {
            val reminders = _state.value.reminders
            _state.value = _state.value.copy(currentDate = date, isLoading = true)
            val remindersForScreenAsList = withContext(Dispatchers.IO) {
                reminders.filter { it.date == _state.value.currentDate }
            }
            _state.value = _state.value.copy(
                remindersForScreenAsList = remindersForScreenAsList,
                isLoading = false
            )
        }
    }
}