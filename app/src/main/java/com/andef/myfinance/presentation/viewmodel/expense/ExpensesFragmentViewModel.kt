package com.andef.myfinance.presentation.viewmodel.expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andef.myfinance.domain.entities.Date
import com.andef.myfinance.domain.entities.ExpenseItem
import com.andef.myfinance.domain.usecases.expense.GetExpensesByDayUseCase
import com.andef.myfinance.domain.usecases.expense.GetExpensesByPeriodUseCase
import javax.inject.Inject

class ExpensesFragmentViewModel @Inject constructor(
    private val getExpensesByDayUseCase: GetExpensesByDayUseCase,
    private val getExpensesByPeriodUseCase: GetExpensesByPeriodUseCase
) : ViewModel() {
    fun getExpensesByDay(date: Date): LiveData<List<ExpenseItem>> {
        return getExpensesByDayUseCase.execute(date)
    }

    fun getExpensesByPeriod(startDate: Date, endDate: Date): LiveData<List<ExpenseItem>> {
        return getExpensesByPeriodUseCase.execute(startDate, endDate)
    }
}