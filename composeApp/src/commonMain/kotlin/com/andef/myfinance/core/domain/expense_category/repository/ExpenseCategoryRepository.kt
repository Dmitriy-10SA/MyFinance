package com.andef.myfinance.core.domain.expense_category.repository

import com.andef.myfinance.core.domain.expense_category.entities.ExpenseCategoryModel
import kotlinx.coroutines.flow.Flow

interface ExpenseCategoryRepository {
    suspend fun addExpenseCategory(expenseCategoryModel: ExpenseCategoryModel): Long
    suspend fun changeExpenseCategory(id: Long, title: String)
    suspend fun deleteExpenseCategory(id: Long)
    suspend fun getExpenseCategories(): List<ExpenseCategoryModel>
    fun getExpenseCategoriesAsFlow(): Flow<List<ExpenseCategoryModel>>
}