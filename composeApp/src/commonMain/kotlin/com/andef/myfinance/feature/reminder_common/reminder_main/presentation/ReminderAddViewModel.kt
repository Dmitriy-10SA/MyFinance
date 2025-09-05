package com.andef.myfinance.feature.reminder_common.reminder_main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.core.domain.reminder.entities.ReminderModel
import com.andef.myfinance.core.domain.reminder.usecases.AddReminderUseCase
import com.andef.myfinance.core.domain.reminder.usecases.ChangeReminderUseCase
import com.andef.myfinance.core.domain.reminder.usecases.GetReminderByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class ReminderAddViewModel(
    private val getReminderByIdUseCase: GetReminderByIdUseCase,
    private val changeReminderUseCase: ChangeReminderUseCase,
    private val addReminderUseCase: AddReminderUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ReminderAddState())
    val state: StateFlow<ReminderAddState> = _state

    fun send(intent: ReminderAddIntent) {
        when (intent) {
            is ReminderAddIntent.ChangeDatePickerVisible -> changeDatePickerVisible(
                isVisible = intent.isVisible
            )

            is ReminderAddIntent.ChangeReminderDate -> changeInput(
                reminderDate = intent.date
            )

            is ReminderAddIntent.ChangeReminderText -> changeInput(
                reminderText = intent.text
            )

            is ReminderAddIntent.ChangeReminderTime -> changeInput(
                reminderTime = intent.time
            )

            is ReminderAddIntent.ChangeTimePickerVisible -> changeTimePickerVisible(
                isVisible = intent.isVisible
            )

            is ReminderAddIntent.InitReminderByLateReminder -> initReminderByLateReminder(
                reminderId = intent.reminderId,
                onError = intent.onError
            )

            is ReminderAddIntent.SaveClick -> saveClick(
                onSuccess = intent.onSuccess,
                onError = intent.onError,
                reminderText = _state.value.reminderText,
                reminderDate = _state.value.reminderDate ?: throw IllegalArgumentException(),
                reminderTime = _state.value.reminderTime ?: throw IllegalArgumentException()
            )
        }
    }

    private fun initReminderByLateReminder(reminderId: Long, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                val reminder =
                    withContext(Dispatchers.IO) { getReminderByIdUseCase.invoke(reminderId) }
                changeInput(
                    reminderText = reminder.text,
                    reminderDate = reminder.date,
                    reminderTime = reminder.time
                )
                _state.value = _state.value.copy(isAdd = false, reminderId = reminderId)
            } catch (_: Exception) {
                onError("Ошибка! Попробуйте ещё раз!")
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    private fun changeTimePickerVisible(isVisible: Boolean) {
        _state.value = _state.value.copy(timePickerVisible = isVisible)
    }

    private fun changeDatePickerVisible(isVisible: Boolean) {
        _state.value = _state.value.copy(datePickerVisible = isVisible)
    }

    private fun saveClick(
        onSuccess: () -> Unit,
        onError: (String) -> Unit,
        reminderText: String,
        reminderDate: LocalDate,
        reminderTime: LocalTime
    ) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                val isAdd = _state.value.isAdd
                val reminderId = _state.value.reminderId
                withContext(Dispatchers.IO) {
                    if (isAdd) {
                        addReminderUseCase.invoke(
                            ReminderModel(
                                id = 0,
                                text = reminderText,
                                date = reminderDate,
                                time = reminderTime
                            )
                        )
                    } else {
                        changeReminderUseCase.invoke(
                            id = reminderId ?: throw IllegalArgumentException(),
                            text = reminderText,
                            date = reminderDate,
                            time = reminderTime
                        )
                    }
                }
                onSuccess()
            } catch (_: Exception) {
                onError("Ошибка! Попробуйте ещё раз!")
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    private fun changeInput(
        reminderText: String = _state.value.reminderText,
        reminderDate: LocalDate? = _state.value.reminderDate,
        reminderTime: LocalTime? = _state.value.reminderTime
    ) {
        _state.value = _state.value.copy(
            reminderText = reminderText,
            reminderDate = reminderDate,
            reminderTime = reminderTime,
            saveButtonEnabled = reminderText.isNotEmpty() && reminderDate != null && reminderTime != null
        )
    }
}