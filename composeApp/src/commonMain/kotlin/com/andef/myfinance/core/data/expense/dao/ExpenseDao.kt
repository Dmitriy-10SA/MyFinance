package com.andef.myfinance.core.data.expense.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.andef.myfinance.db.Expense
import com.andef.myfinance.db.ExpenseQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class ExpenseDao(private val queries: ExpenseQueries) {
    fun changeAllExpenseCategoryByOldCategory(old: String, new: String) =
        queries.changeAllExpenseCategory(new, old)

    fun deleteAllExpensesByCategory(category: String) =
        queries.deleteAllExpensesByCategory(category)

    fun getExpenseById(id: Long): Expense = queries.getExpenseById(id).executeAsOne()

    fun insert(expense: Expense) = queries.insertExpense(
        expense.amount,
        expense.category,
        expense.date,
        expense.note
    )

    fun update(expense: Expense) = queries.updateExpense(
        expense.amount,
        expense.category,
        expense.date,
        expense.note,
        expense.id
    )

    fun deleteById(id: Long) = queries.deleteExpenseById(id)

    fun getExpensesByDateRangeFlow(startDate: Int, endDate: Int): Flow<List<Expense>> =
        queries.getExpensesByDateRange(startDate.toLong(), endDate.toLong())
            .asFlow()
            .flowOn(Dispatchers.IO)
            .mapToList(Dispatchers.IO)

    fun getExpensesByDateRange(startDate: Int, endDate: Int): List<Expense> =
        queries.getExpensesByDateRange(startDate.toLong(), endDate.toLong())
            .executeAsList()
}