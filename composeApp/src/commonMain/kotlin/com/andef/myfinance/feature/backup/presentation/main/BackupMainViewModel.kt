package com.andef.myfinance.feature.backup.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.core.domain.backup.entities.BackupData
import com.andef.myfinance.core.domain.expense_common.expense.entities.ExpenseModel
import com.andef.myfinance.core.domain.expense_common.expense.usecases.AddExpenseUseCase
import com.andef.myfinance.core.domain.expense_common.expense.usecases.GetExpensesByDateRangeUseCase
import com.andef.myfinance.core.domain.expense_common.expense_category.usecases.AddExpenseCategoryUseCase
import com.andef.myfinance.core.domain.expense_common.expense_category.usecases.GetExpenseCategoriesUseCase
import com.andef.myfinance.core.domain.income_common.income.entities.IncomeModel
import com.andef.myfinance.core.domain.income_common.income.usecases.AddIncomeUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.GetIncomesByDateRangeUseCase
import com.andef.myfinance.core.domain.income_common.income_category.usecases.AddIncomeCategoryUseCase
import com.andef.myfinance.core.domain.income_common.income_category.usecases.GetIncomeCategoriesUseCase
import com.andef.myfinance.core.domain.preferences.usecases.GetUsernameUseCase
import com.andef.myfinance.core.domain.preferences.usecases.SetUsernameUseCase
import com.andef.myfinance.core.domain.reminder.entities.ReminderModel
import com.andef.myfinance.core.domain.reminder.usecases.AddReminderUseCase
import com.andef.myfinance.core.domain.reminder.usecases.GetRemindersAsListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate

class BackupMainViewModel (
    private val getIncomeCategoriesUseCase: GetIncomeCategoriesUseCase,
    private val getIncomesByDateRangeUseCase: GetIncomesByDateRangeUseCase,
    private val getExpensesByDateRangeUseCase: GetExpensesByDateRangeUseCase,
    private val getRemindersAsListUseCase: GetRemindersAsListUseCase,
    private val getUsernameUseCase: GetUsernameUseCase,
    private val getExpenseCategoriesUseCase: GetExpenseCategoriesUseCase,
    private val addIncomeUseCase: AddIncomeUseCase,
    private val addExpenseUseCase: AddExpenseUseCase,
    private val addReminderUseCase: AddReminderUseCase,
    private val addIncomeCategoryUseCase: AddIncomeCategoryUseCase,
    private val addExpenseCategoryUseCase: AddExpenseCategoryUseCase,
    private val setUsernameUseCase: SetUsernameUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(BackupMainState())
    val state: StateFlow<BackupMainState> = _state

    fun send(intent: BackupMainIntent) {
        when (intent) {
            is BackupMainIntent.RestoreData -> {
                restoreData(
                    data = intent.data,
                    onSuccess = intent.onSuccess,
                    onError = intent.onError
                )
            }

            is BackupMainIntent.SaveData -> {
                saveData(intent.onSuccess, intent.onError)
            }
        }
    }

    private fun saveData(onSuccess: (BackupData) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val localDateMin = LocalDate(-999999999, 1, 1)
                val localDateMax = LocalDate(999999999, 12, 31)
                _state.value = _state.value.copy(isLoading = true, isErrorSnackbar = false)
                val allIncomes = withContext(Dispatchers.IO) {
                    getIncomesByDateRangeUseCase.invoke(localDateMin, localDateMax).map {
                        IncomeModel(
                            id = it.id,
                            amount = it.amount,
                            date = it.date,
                            note = it.note,
                            category = it.category
                        )
                    }
                }
                val allExpenses = withContext(Dispatchers.IO) {
                    getExpensesByDateRangeUseCase.invoke(localDateMin, localDateMax).map {
                        ExpenseModel(
                            id = it.id,
                            amount = it.amount,
                            date = it.date,
                            note = it.note,
                            category = it.category
                        )
                    }
                }
                val allReminders = withContext(Dispatchers.IO) {
                    getRemindersAsListUseCase.invoke(localDateMin, localDateMax).map {
                        ReminderModel(
                            id = it.id,
                            date = it.date,
                            text = it.text,
                            time = it.time
                        )
                    }
                }
                val incomeCategories = withContext(Dispatchers.IO) {
                    getIncomeCategoriesUseCase.invoke()
                }
                val expenseCategories = withContext(Dispatchers.IO) {
                    getExpenseCategoriesUseCase.invoke()
                }
                val username = getUsernameUseCase.invoke()
                val backupData = BackupData(
                    allIncomeModels = allIncomes,
                    allExpenseModels = allExpenses,
                    allReminderModels = allReminders,
                    username = username,
                    incomeCategories = incomeCategories,
                    expenseCategories = expenseCategories
                )
                onSuccess(backupData)
            } catch (_: Exception) {
                _state.value = _state.value.copy(isErrorSnackbar = true)
                onError("Ошибка! Попробуйте ещё раз!")
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
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