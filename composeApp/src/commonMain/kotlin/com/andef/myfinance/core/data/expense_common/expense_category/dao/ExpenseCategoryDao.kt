package com.andef.myfinance.core.data.expense_common.expense_category.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.andef.myfinance.db.ExpenseCategoryQueries
import com.andef.myfinance.db.Expense_category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class ExpenseCategoryDao(private val queries: ExpenseCategoryQueries) {
    fun addExpenseCategory(expenseCategory: Expense_category) =
        queries.addExpenseCategory(expenseCategory.title)

    fun changeExpenseCategory(id: Long, title: String) =
        queries.changeExpenseCategory(title, id)

    fun deleteExpenseCategory(id: Long) = queries.deleteExpenseCategory(id)

    fun getExpenseCategories(): List<Expense_category> =
        queries.getExpenseCategories().executeAsList()

    fun getExpenseCategoriesAsFlow(): Flow<List<Expense_category>> =
        queries.getExpenseCategories()
            .asFlow()
            .flowOn(Dispatchers.IO)
            .mapToList(Dispatchers.IO)
}