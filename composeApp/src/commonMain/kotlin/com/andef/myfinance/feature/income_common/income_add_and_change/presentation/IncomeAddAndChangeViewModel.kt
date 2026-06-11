package com.andef.myfinance.feature.income_common.income_add_and_change.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.core.domain.income_common.income.entities.IncomeModel
import com.andef.myfinance.core.domain.income_common.income.usecases.AddIncomeUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.GetIncomeByIdUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.UpdateIncomeUseCase
import com.andef.myfinance.core.domain.income_common.income_category.entities.BaseIncomeCategory
import com.andef.myfinance.core.domain.income_common.income_category.entities.IncomeCategoryModel
import com.andef.myfinance.core.domain.income_common.income_category.usecases.GetIncomeCategoriesUseCase
import com.andef.myfinance.core.utils.getters.getTitleForIncome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IncomeAddAndChangeViewModel(
    private val getIncomeByIdUseCase: GetIncomeByIdUseCase,
    private val addIncomeUseCase: AddIncomeUseCase,
    private val updateIncomeUseCase: UpdateIncomeUseCase,
    private val getIncomeCategoriesUseCase: GetIncomeCategoriesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(IncomeAddAndChangeState())
    val state: StateFlow<IncomeAddAndChangeState> = _state

    fun send(intent: IncomeAddAndChangeIntent) {
        when (intent) {
            is IncomeAddAndChangeIntent.ChangeAmount -> {
                _state.value = _state.value.copy(amount = intent.amount)
                buttonStateCheck()
            }

            is IncomeAddAndChangeIntent.ChangeCategory -> {
                _state.value = _state.value.copy(category = intent.category)
                buttonStateCheck()
            }

            is IncomeAddAndChangeIntent.ChangeDate -> {
                _state.value = _state.value.copy(date = intent.date)
                buttonStateCheck()
            }

            is IncomeAddAndChangeIntent.ChangeDatePickerVisible -> {
                _state.value = _state.value.copy(datePickerVisible = intent.isVisible)
            }

            is IncomeAddAndChangeIntent.ChangeNote -> {
                _state.value = _state.value.copy(note = intent.note)
                buttonStateCheck()
            }

            is IncomeAddAndChangeIntent.InitIncome -> {
                initIncome(intent.incomeId, intent.onError)
                buttonStateCheck()
            }

            is IncomeAddAndChangeIntent.SaveClick -> {
                save(intent.onSuccess, intent.onError)
            }
        }
    }

    private fun buttonStateCheck() {
        _state.value = _state.value.copy(
            saveButtonEnabled = _state.value.amount != null && _state.value.category != null &&
                    _state.value.date != null && _state.value.amount != 0L
        )
    }

    private fun save(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                val isAdd = _state.value.isAdd
                val incomeId = _state.value.incomeId
                val amount = _state.value.amount ?: throw IllegalArgumentException()
                val category = _state.value.category ?: throw IllegalArgumentException()
                val date = _state.value.date ?: throw IllegalArgumentException()
                val note = _state.value.note
                withContext(Dispatchers.IO) {
                    when (isAdd) {
                        true -> addIncomeUseCase.invoke(
                            IncomeModel(
                                0,
                                amount,
                                category,
                                date,
                                note
                            )
                        )

                        false -> {
                            val id = incomeId ?: throw IllegalArgumentException()
                            updateIncomeUseCase.invoke(id, amount, category, date, note)
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

    private fun initIncome(incomeId: Long?, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                if (incomeId != null) {
                    val income =
                        withContext(Dispatchers.IO) { getIncomeByIdUseCase.invoke(incomeId) }
                    _state.value = _state.value.copy(
                        amount = income.amount,
                        category = IncomeCategoryModel(
                            id = 0,
                            title = getTitleForIncome(income.category.title)
                        ),
                        date = income.date,
                        note = income.note,
                        isAdd = false,
                        incomeId = incomeId
                    )
                }
                val baseIncomeCategories =
                    BaseIncomeCategory.entries.map {
                        IncomeCategoryModel(
                            id = 0,
                            title = it.titleForUser
                        )
                    }
                val userIncomeCategories =
                    withContext(Dispatchers.IO) { getIncomeCategoriesUseCase.invoke() }
                _state.value =
                    _state.value.copy(incomeCategories = baseIncomeCategories + userIncomeCategories)
            } catch (_: Exception) {
                onError("Ошибка! Попробуйте ещё раз!")
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}
