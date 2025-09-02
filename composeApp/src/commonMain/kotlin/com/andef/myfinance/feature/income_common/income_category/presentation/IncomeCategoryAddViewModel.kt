package com.andef.myfinance.feature.income_common.income_category.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.core.domain.income_common.income.usecases.ChangeAllIncomeCategoryByOldCategoryUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.DeleteAllIncomesByCategoryUseCase
import com.andef.myfinance.core.domain.income_common.income_category.entities.BaseIncomeCategory
import com.andef.myfinance.core.domain.income_common.income_category.entities.IncomeCategoryModel
import com.andef.myfinance.core.domain.income_common.income_category.usecases.AddIncomeCategoryUseCase
import com.andef.myfinance.core.domain.income_common.income_category.usecases.ChangeIncomeCategoryUseCase
import com.andef.myfinance.core.domain.income_common.income_category.usecases.DeleteIncomeCategoryUseCase
import com.andef.myfinance.core.domain.income_common.income_category.usecases.GetIncomeCategoriesAsFlowUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IncomeCategoryAddViewModel(
    private val addIncomeCategoryUseCase: AddIncomeCategoryUseCase,
    private val changeIncomeCategoryUseCase: ChangeIncomeCategoryUseCase,
    private val deleteIncomeCategoryUseCase: DeleteIncomeCategoryUseCase,
    private val getIncomeCategoriesAsFlowUseCase: GetIncomeCategoriesAsFlowUseCase,
    private val changeAllIncomeCategoryByOldCategoryUseCase: ChangeAllIncomeCategoryByOldCategoryUseCase,
    private val deleteAllIncomesByCategoryUseCase: DeleteAllIncomesByCategoryUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(IncomeCategoryAddState())
    val state: StateFlow<IncomeCategoryAddState> = _state

    fun send(intent: IncomeCategoryAddIntent) {
        when (intent) {
            is IncomeCategoryAddIntent.AddIncomeCategory -> {
                val baseIncomeCategories =
                    BaseIncomeCategory.entries.map { it.titleForUser.lowercase() }
                val incomeCategories =
                    _state.value.incomeCategories.map { it.title.lowercase() } + baseIncomeCategories
                val title = intent.title
                val onError = intent.onError
                actionWithIncomeCategory(
                    onError = intent.onError,
                    action = {
                        if (title.lowercase() !in incomeCategories) {
                            addIncomeCategoryUseCase.invoke(
                                IncomeCategoryModel(
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

            is IncomeCategoryAddIntent.AddOrChangeIncomeCategoryDialogVisible -> {
                changeDialogsVisible(addOrChangeDialogVisible = intent.isVisible)
            }

            is IncomeCategoryAddIntent.ChangeOldTitle -> {
                _state.value = _state.value.copy(oldTitle = intent.title)
            }

            is IncomeCategoryAddIntent.ChangeActionsDialogVisible -> {
                changeDialogsVisible(actionsDialogVisible = intent.isVisible)
            }

            is IncomeCategoryAddIntent.ChangeCurrentIncomeCategoryId -> {
                changeCurrentIncomeCategory(id = intent.id)
            }

            is IncomeCategoryAddIntent.ChangeCurrentIncomeCategoryTitle -> {
                changeCurrentIncomeCategory(title = intent.title)
            }

            is IncomeCategoryAddIntent.ChangeIncomeCategory -> {
                actionWithIncomeCategory(
                    onError = intent.onError,
                    action = {
                        changeIncomeCategoryUseCase.invoke(intent.id, intent.title)
                        changeAllIncomeCategoryByOldCategoryUseCase.invoke(
                            old = intent.oldTitle,
                            new = intent.title
                        )
                    }
                )
            }

            is IncomeCategoryAddIntent.DeleteIncomeCategory -> {
                actionWithIncomeCategory(
                    onError = intent.onError,
                    action = {
                        deleteIncomeCategoryUseCase.invoke(intent.id)
                        deleteAllIncomesByCategoryUseCase.invoke(intent.title)
                    }
                )
            }

            is IncomeCategoryAddIntent.ChangeDeleteDialogVisible -> {
                _state.value = _state.value.copy(showDeleteDialog = intent.isVisible)
            }

            IncomeCategoryAddIntent.SubscribeForIncomeCategories -> {
                subscribeForIncomeCategories()
            }
        }
    }

    private var isFirstStart = true
    private var job: Job? = null
    private fun subscribeForIncomeCategories() {
        if (isFirstStart == true || _state.value.isError == true) {
            job?.cancel()
            job = viewModelScope.launch {
                getIncomeCategoriesAsFlowUseCase.invoke()
                    .onStart {
                        _state.value = _state.value.copy(isLoading = true, isError = false)
                    }
                    .catch {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            isError = true,
                            incomeCategories = emptyList()
                        )
                    }
                    .collect {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            incomeCategories = it,
                            isError = false
                        )
                    }
            }
        }
    }

    private fun actionWithIncomeCategory(action: suspend () -> Unit, onError: (String) -> Unit) {
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
        addOrChangeDialogVisible: Boolean = _state.value.addOrChangeIncomeCategoryDialogVisible,
        actionsDialogVisible: Boolean = _state.value.actionsDialogVisible
    ) {
        _state.value = _state.value.copy(
            addOrChangeIncomeCategoryDialogVisible = addOrChangeDialogVisible,
            actionsDialogVisible = actionsDialogVisible
        )
    }

    private fun changeCurrentIncomeCategory(
        id: Long? = _state.value.currentIncomeCategoryId,
        title: String = _state.value.currentIncomeCategoryTitle
    ) {
        _state.value = _state.value.copy(
            currentIncomeCategoryId = id,
            currentIncomeCategoryTitle = title,
            addOrChangeIncomeCategoryButtonEnabled = title.length >= 2
        )
    }
}