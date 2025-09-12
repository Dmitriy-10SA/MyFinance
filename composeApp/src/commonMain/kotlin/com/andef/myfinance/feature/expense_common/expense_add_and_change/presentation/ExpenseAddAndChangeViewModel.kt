package com.andef.myfinance.feature.expense_common.expense_add_and_change.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.core.domain.expense_common.expense.entities.ExpenseModel
import com.andef.myfinance.core.domain.expense_common.expense.usecases.AddExpenseUseCase
import com.andef.myfinance.core.domain.expense_common.expense.usecases.GetExpenseByIdUseCase
import com.andef.myfinance.core.domain.expense_common.expense.usecases.UpdateExpenseUseCase
import com.andef.myfinance.core.domain.expense_common.expense_category.entities.BaseExpenseCategory
import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel
import com.andef.myfinance.core.domain.expense_common.expense_category.usecases.GetExpenseCategoriesUseCase
import com.andef.myfinance.core.utils.getters.getTitleForExpense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpenseAddAndChangeViewModel(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val getExpenseByIdUseCase: GetExpenseByIdUseCase,
    private val getExpenseCategoriesUseCase: GetExpenseCategoriesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ExpenseAddAndChangeState())
    val state: StateFlow<ExpenseAddAndChangeState> = _state

    fun send(intent: ExpenseAddAndChangeIntent) {
        when (intent) {
            is ExpenseAddAndChangeIntent.ChangeAmount -> {
                _state.value = _state.value.copy(amount = intent.amount)
                buttonStateCheck()
            }

            is ExpenseAddAndChangeIntent.ChangeCategory -> {
                _state.value = _state.value.copy(category = intent.category)
                buttonStateCheck()
            }

            is ExpenseAddAndChangeIntent.ChangeDate -> {
                _state.value = _state.value.copy(date = intent.date)
                buttonStateCheck()
            }

            is ExpenseAddAndChangeIntent.ChangeDatePickerVisible -> {
                _state.value = _state.value.copy(datePickerVisible = intent.isVisible)
            }

            is ExpenseAddAndChangeIntent.ChangeNote -> {
                _state.value = _state.value.copy(note = intent.note)
                buttonStateCheck()
            }

            is ExpenseAddAndChangeIntent.InitExpense -> {
                initExpense(intent.expenseId, intent.onError)
            }

            is ExpenseAddAndChangeIntent.SaveClick -> {
                save(intent.onSuccess, intent.onError)
            }
        }
    }

    private fun save(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                val isAdd = _state.value.isAdd
                val expenseId = _state.value.expenseId
                val amount = _state.value.amount ?: throw IllegalArgumentException()
                val category = _state.value.category ?: throw IllegalArgumentException()
                val date = _state.value.date ?: throw IllegalArgumentException()
                val note = _state.value.note
                withContext(Dispatchers.IO) {
                    when (isAdd) {
                        true -> addExpenseUseCase.invoke(
                            ExpenseModel(
                                0,
                                amount,
                                category,
                                date,
                                note
                            )
                        )

                        false -> {
                            val id = expenseId ?: throw IllegalArgumentException()
                            updateExpenseUseCase.invoke(id, amount, category, date, note)
                        }
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


    private fun initExpense(id: Long?, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                if (id != null) {
                    val expense = withContext(Dispatchers.IO) { getExpenseByIdUseCase.invoke(id) }
                    _state.value = _state.value.copy(
                        amount = expense.amount,
                        category = ExpenseCategoryModel(
                            id = 0,
                            title = getTitleForExpense(expense.category.title)
                        ),
                        date = expense.date,
                        note = expense.note,
                        isAdd = false,
                        expenseId = id
                    )
                }
                val baseExpenseCategories =
                    BaseExpenseCategory.entries.map {
                        ExpenseCategoryModel(
                            id = 0,
                            title = it.titleForUser
                        )
                    }
                val userExpenseCategories =
                    withContext(Dispatchers.IO) { getExpenseCategoriesUseCase.invoke() }
                _state.value =
                    _state.value.copy(expenseCategories = baseExpenseCategories + userExpenseCategories)
            } catch (_: Exception) {
                onError("Ошибка! Попробуйте ещё раз!")
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    private fun buttonStateCheck() {
        _state.value = _state.value.copy(
            saveButtonEnabled = _state.value.amount != null && _state.value.category != null &&
                    _state.value.date != null && _state.value.amount != 0.0
        )
    }
}