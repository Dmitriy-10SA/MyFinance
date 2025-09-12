package com.andef.myfinance.feature.backup.presentation.start

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.core.domain.backup.entities.BackupData
import com.andef.myfinance.core.domain.expense_common.expense.usecases.AddExpenseUseCase
import com.andef.myfinance.core.domain.expense_common.expense_category.usecases.AddExpenseCategoryUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.AddIncomeUseCase
import com.andef.myfinance.core.domain.income_common.income_category.usecases.AddIncomeCategoryUseCase
import com.andef.myfinance.core.domain.preferences.usecases.SetIsFirstStartUseCase
import com.andef.myfinance.core.domain.preferences.usecases.SetUsernameUseCase
import com.andef.myfinance.core.domain.reminder.usecases.AddReminderUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BackupStartViewModel(
    private val addIncomeUseCase: AddIncomeUseCase,
    private val addExpenseUseCase: AddExpenseUseCase,
    private val addReminderUseCase: AddReminderUseCase,
    private val addIncomeCategoryUseCase: AddIncomeCategoryUseCase,
    private val addExpenseCategoryUseCase: AddExpenseCategoryUseCase,
    private val setUsernameUseCase: SetUsernameUseCase,
    private val setIsFirstStartUseCase: SetIsFirstStartUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(BackupStartState())
    val state: StateFlow<BackupStartState> = _state

    fun send(intent: BackupStartIntent) {
        when (intent) {
            is BackupStartIntent.RestoreData -> restoreData(
                data = intent.data,
                onSuccess = intent.onSuccess,
                onError = intent.onError
            )

            is BackupStartIntent.HelpBottomSheetVisibleChange -> helpBottomSheetVisibleChange(
                isVisible = intent.isVisible
            )
        }
    }

    private fun helpBottomSheetVisibleChange(isVisible: Boolean) {
        _state.value = _state.value.copy(helpBottomSheetVisible = isVisible)
    }

    private fun restoreData(
        data: BackupData,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, isErrorSnackbar = false)
                withContext(Dispatchers.IO) {
                    with(data) {
                        incomeCategories.forEach { addIncomeCategoryUseCase.invoke(it) }
                        expenseCategories.forEach { addExpenseCategoryUseCase.invoke(it) }
                        allIncomeModels.forEach { addIncomeUseCase.invoke(it) }
                        allExpenseModels.forEach { addExpenseUseCase.invoke(it) }
                        allReminderModels.forEach { addReminderUseCase.invoke(it) }
                    }
                }
                setUsernameUseCase.invoke(data.username)
                setIsFirstStartUseCase.invoke(false)
                onSuccess("Данные успешно восстановлены!")
            } catch (_: Exception) {
                _state.value = _state.value.copy(isErrorSnackbar = true)
                onError("Ошибка! Попробуйте ещё раз!")
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}