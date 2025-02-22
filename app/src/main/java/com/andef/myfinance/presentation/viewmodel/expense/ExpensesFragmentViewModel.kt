package com.andef.myfinance.presentation.viewmodel.expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.domain.entities.Date
import com.andef.myfinance.domain.entities.ExpenseItem
import com.andef.myfinance.domain.usecases.expense.GetExpensesByDayUseCase
import com.andef.myfinance.domain.usecases.expense.GetExpensesByPeriodUseCase
import com.andef.myfinance.domain.usecases.expense.GetFullExpenseByDayUseCase
import com.andef.myfinance.domain.usecases.expense.GetFullExpenseByPeriodUseCase
import com.andef.myfinance.domain.usecases.expense.RemoveExpenseUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExpensesFragmentViewModel @Inject constructor(
    private val getExpensesByDayUseCase: GetExpensesByDayUseCase,
    private val getExpensesByPeriodUseCase: GetExpensesByPeriodUseCase,
    private val getFullExpenseByDayUseCase: GetFullExpenseByDayUseCase,
    private val getFullExpenseByPeriodUseCase: GetFullExpenseByPeriodUseCase,
    private val removeExpenseUseCase: RemoveExpenseUseCase
) : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

    fun getExpensesByDay(date: Date): LiveData<List<ExpenseItem>> {
        return getExpensesByDayUseCase.execute(date)
    }

    fun getExpensesByPeriod(startDate: Date, endDate: Date): LiveData<List<ExpenseItem>> {
        return getExpensesByPeriodUseCase.execute(startDate, endDate)
    }

    fun getFullExpenseByDay(date: Date): LiveData<Double> {
        return getFullExpenseByDayUseCase.execute(date)
    }

    fun getFullExpenseByPeriod(startDate: Date, endDate: Date): LiveData<Double> {
        return getFullExpenseByPeriodUseCase.execute(startDate, endDate)
    }

    fun removeExpense(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            removeExpenseUseCase.execute(id)
        }
    }
}