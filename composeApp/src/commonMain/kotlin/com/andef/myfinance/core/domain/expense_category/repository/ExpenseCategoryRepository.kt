package com.andef.myfinance.core.domain.expense_category.repository

import com.andef.myfinance.core.domain.expense_category.entities.ExpenseCategory
import kotlinx.coroutines.flow.Flow

interface ExpenseCategoryRepository {
    suspend fun addExpenseCategory(expenseCategory: ExpenseCategory): Long
    suspend fun changeExpenseCategory(id: Long, title: String)
    suspend fun deleteExpenseCategory(id: Long)
    suspend fun getExpenseCategories(): List<ExpenseCategory>
    fun getExpenseCategoriesAsFlow(): Flow<List<ExpenseCategory>>
}