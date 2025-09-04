package com.andef.myfinance.feature.expense_common.expense_category.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.core.domain.expense_common.expense.usecases.ChangeAllExpenseCategoryByOldCategoryUseCase
import com.andef.myfinance.core.domain.expense_common.expense.usecases.DeleteAllExpensesByCategoryUseCase
import com.andef.myfinance.core.domain.expense_common.expense_category.entities.BaseExpenseCategory
import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel
import com.andef.myfinance.core.domain.expense_common.expense_category.usecases.AddExpenseCategoryUseCase
import com.andef.myfinance.core.domain.expense_common.expense_category.usecases.ChangeExpenseCategoryUseCase
import com.andef.myfinance.core.domain.expense_common.expense_category.usecases.DeleteExpenseCategoryUseCase
import com.andef.myfinance.core.domain.expense_common.expense_category.usecases.GetExpenseCategoriesAsFlowUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpenseCategoryAddViewModel(
    private val addExpenseCategoryUseCase: AddExpenseCategoryUseCase,
    private val changeExpenseCategoryUseCase: ChangeExpenseCategoryUseCase,
    private val deleteExpenseCategoryUseCase: DeleteExpenseCategoryUseCase,
    private val getExpenseCategoriesAsFlowUseCase: GetExpenseCategoriesAsFlowUseCase,
    private val changeAllExpenseCategoryByOldCategoryUseCase: ChangeAllExpenseCategoryByOldCategoryUseCase,
    private val deleteAllExpensesByCategoryUseCase: DeleteAllExpensesByCategoryUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ExpenseCategoryAddState())
    val state: StateFlow<ExpenseCategoryAddState> = _state

    fun send(intent: ExpenseCategoryAddIntent) {
        when (intent) {
            is ExpenseCategoryAddIntent.AddExpenseCategory -> {
                val baseExpenseCategories =
                    BaseExpenseCategory.entries.map { it.titleForUser.lowercase() }
                val expenseCategories =
                    _state.value.expenseCategories.map { it.title.lowercase() } + baseExpenseCategories
                val title = intent.title
                val onError = intent.onError
                actionWithExpenseCategory(
                    onError = intent.onError,
                    action = {
                        if (title.lowercase() !in expenseCategories) {
                            addExpenseCategoryUseCase.invoke(
                                ExpenseCategoryModel(
                                    id = 0,
                                    title = title
                                )
                            )
                        } else {
                            onError("Такая категория уже существует!")
                        }
                    }
                )
            }

            is ExpenseCategoryAddIntent.AddOrChangeExpenseCategoryDialogVisible -> {
                changeDialogsVisible(addOrChangeDialogVisible = intent.isVisible)
            }

            is ExpenseCategoryAddIntent.ChangeOldTitle -> {
                _state.value = _state.value.copy(oldTitle = intent.title)
            }

            is ExpenseCategoryAddIntent.ChangeActionsDialogVisible -> {
                changeDialogsVisible(actionsDialogVisible = intent.isVisible)
            }

            is ExpenseCategoryAddIntent.ChangeCurrentExpenseCategoryId -> {
                changeCurrentExpenseCategory(id = intent.id)
            }

            is ExpenseCategoryAddIntent.ChangeCurrentExpenseCategoryTitle -> {
                changeCurrentExpenseCategory(title = intent.title)
            }

            is ExpenseCategoryAddIntent.ChangeExpenseCategory -> {
                actionWithExpenseCategory(
                    onError = intent.onError,
                    action = {
                        changeExpenseCategoryUseCase.invoke(intent.id, intent.title)
                        changeAllExpenseCategoryByOldCategoryUseCase.invoke(
                            old = intent.oldTitle,
                            new = intent.title
                        )
                    }
                )
            }

            is ExpenseCategoryAddIntent.DeleteExpenseCategory -> {
                actionWithExpenseCategory(
                    onError = intent.onError,
                    action = {
                        deleteExpenseCategoryUseCase.invoke(intent.id)
                        deleteAllExpensesByCategoryUseCase.invoke(intent.title)
                    }
                )
            }

            is ExpenseCategoryAddIntent.ChangeDeleteDialogVisible -> {
                _state.value = _state.value.copy(showDeleteDialog = intent.isVisible)
            }

            ExpenseCategoryAddIntent.SubscribeForExpenseCategories -> {
                subscribeForExpenseCategories()
            }
        }
    }

    private var isFirstStart = true
    private var job: Job? = null
    private fun subscribeForExpenseCategories() {
        if (isFirstStart == true || _state.value.isError == true) {
            job?.cancel()
            job = viewModelScope.launch {
                getExpenseCategoriesAsFlowUseCase.invoke()
                    .onStart {
                        _state.value = _state.value.copy(isLoading = true, isError = false)
                    }
                    .catch {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            isError = true,
                            expenseCategories = emptyList()
                        )
                    }
                    .collect {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            expenseCategories = it,
                            isError = false
                        )
                    }
            }
        }
    }

    private fun actionWithExpenseCategory(action: suspend () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                withContext(Dispatchers.IO) { action() }
            } catch (_: Exception) {
                onError("Ошибка! Попробуйте ещё раз!")
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    private fun changeDialogsVisible(
        addOrChangeDialogVisible: Boolean = _state.value.addOrChangeExpenseCategoryDialogVisible,
        actionsDialogVisible: Boolean = _state.value.actionsDialogVisible
    ) {
        _state.value = _state.value.copy(
            addOrChangeExpenseCategoryDialogVisible = addOrChangeDialogVisible,
            actionsDialogVisible = actionsDialogVisible
        )
    }

    private fun changeCurrentExpenseCategory(
        id: Long? = _state.value.currentExpenseCategoryId,
        title: String = _state.value.currentExpenseCategoryTitle
    ) {
        _state.value = _state.value.copy(
            currentExpenseCategoryId = id,
            currentExpenseCategoryTitle = title,
            addOrChangeExpenseCategoryButtonEnabled = title.length >= 2
        )
    }
}