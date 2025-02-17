package com.andef.myfinance.domain.repository.expense

import androidx.lifecycle.LiveData
import com.andef.myfinance.domain.entities.Date
import com.andef.myfinance.domain.entities.ExpenseItem

interface ExpenseRepository {
    fun getExpensesByDay(date: Date): LiveData<List<ExpenseItem>>
    fun getExpensesByPeriod(startDate: Date, endDate: Date): LiveData<List<ExpenseItem>>
    suspend fun addExpense(expenseItem: ExpenseItem)
    suspend fun removeExpense(id: Int)
}