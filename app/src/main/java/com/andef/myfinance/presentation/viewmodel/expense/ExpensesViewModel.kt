package com.andef.myfinance.presentation.viewmodel.expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.domain.entities.ExpenseItem
import com.andef.myfinance.domain.entities.IncomeItem
import com.andef.myfinance.domain.usecases.expense.AddExpenseUseCase
import com.andef.myfinance.domain.usecases.expense.GetExpensesByDayUseCase
import com.andef.myfinance.domain.usecases.expense.GetExpensesByPeriodUseCase
import com.andef.myfinance.domain.usecases.expense.GetFullExpenseByDayUseCase
import com.andef.myfinance.domain.usecases.expense.GetFullExpenseByPeriodUseCase
import com.andef.myfinance.domain.usecases.expense.RemoveExpenseUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExpensesViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
) : ViewModel() {
    private val addExceptionHandler = CoroutineExceptionHandler { _, _ ->
        _isSuccessAdd.value = false
    }

    private val _isSuccessAdd = MutableLiveData<Boolean>()
    val isSuccessAdd: LiveData<Boolean> = _isSuccessAdd

    fun addExpense(expenseItem: ExpenseItem) {
        viewModelScope.launch(addExceptionHandler) {
            addExpenseUseCase.execute(expenseItem)
            _isSuccessAdd.value = true
        }
    }
}