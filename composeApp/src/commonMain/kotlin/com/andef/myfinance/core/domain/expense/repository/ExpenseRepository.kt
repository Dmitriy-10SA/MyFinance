package com.andef.myfinance.core.domain.expense.repository

import com.andef.myfinance.core.domain.expense.entities.Expense
import com.andef.myfinance.core.domain.expense_category.entities.ExpenseCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface ExpenseRepository {
    suspend fun getExpenseById(id: Long): Expense
    suspend fun addExpense(expense: Expense)
    suspend fun updateExpense(
        id: Long,
        amount: Double,
        category: ExpenseCategory,
        date: LocalDate,
        note: String?
    )
    suspend fun changeAllExpenseCategoryByOldCategory(old: String, new: String)
    suspend fun deleteAllExpensesByCategory(category: String)
    suspend fun deleteExpense(id: Long)
    fun getExpensesByDateRangeFlow(startDate: LocalDate, endDate: LocalDate): Flow<List<Expense>>
    suspend fun getExpensesByDateRange(startDate: LocalDate, endDate: LocalDate): List<Expense>
}