package com.andef.myfinance.core.domain.expense_common.expense.repository

import com.andef.myfinance.core.domain.expense_common.expense.entities.ExpenseModel
import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface ExpenseRepository {
    suspend fun getExpenseById(id: Long): ExpenseModel
    suspend fun addExpense(expenseModel: ExpenseModel)
    suspend fun updateExpense(
        id: Long,
        amount: Double,
        category: ExpenseCategoryModel,
        date: LocalDate,
        note: String?
    )
    suspend fun changeAllExpenseCategoryByOldCategory(old: String, new: String)
    suspend fun deleteAllExpensesByCategory(category: String)
    suspend fun deleteExpense(id: Long)
    fun getExpensesByDateRangeFlow(startDate: LocalDate, endDate: LocalDate): Flow<List<ExpenseModel>>
    suspend fun getExpensesByDateRange(startDate: LocalDate, endDate: LocalDate): List<ExpenseModel>
}