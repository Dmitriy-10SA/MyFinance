package com.andef.myfinance.presentation.viewmodel.expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.domain.entities.ExpenseItem
import com.andef.myfinance.domain.usecases.expense.AddExpenseUseCase
import com.andef.myfinance.domain.usecases.expense.RemoveExpenseUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExpensesViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val removeExpenseUseCase: RemoveExpenseUseCase
) : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _isSuccess.value = false
    }

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun addExpense(expenseItem: ExpenseItem) {
        viewModelScope.launch(exceptionHandler) {
            addExpenseUseCase.execute(expenseItem)
            _isSuccess.value = true
        }
    }

    fun removeExpense(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            removeExpenseUseCase.execute(id)
            _isSuccess.value = true
        }
    }

    fun changeExpense(id: Int, expenseItem: ExpenseItem) {
        viewModelScope.launch(exceptionHandler) {
            removeExpenseUseCase.execute(id)
            addExpenseUseCase.execute(expenseItem)
            _isSuccess.value = true
        }
    }
}