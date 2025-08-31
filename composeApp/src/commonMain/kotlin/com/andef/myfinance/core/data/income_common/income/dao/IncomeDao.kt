package com.andef.myfinance.core.data.income_common.income.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.andef.myfinance.db.Income
import com.andef.myfinance.db.IncomeQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class IncomeDao(private val queries: IncomeQueries) {
    fun changeAllIncomeCategoryByOldCategory(old: String, new: String) =
        queries.changeAllIncomeCategory(new, old)

    fun deleteAllIncomesByCategory(category: String) = queries.deleteAllIncomesByCategory(category)

    fun getIncomeById(id: Long): Income = queries.getIncomeById(id).executeAsOne()

    fun insert(income: Income) = queries.insertIncome(
        income.amount,
        income.category,
        income.date,
        income.note
    )

    fun update(income: Income) = queries.updateIncome(
        income.amount,
        income.category,
        income.date,
        income.note,
        income.id
    )

    fun deleteById(id: Long) = queries.deleteIncomeById(id)

    fun getIncomesByDateRangeFlow(startDate: Int, endDate: Int): Flow<List<Income>> =
        queries.getIncomesByDateRange(startDate.toLong(), endDate.toLong())
            .asFlow()
            .flowOn(Dispatchers.IO)
            .mapToList(Dispatchers.IO)

    fun getIncomesByDateRange(startDate: Int, endDate: Int): List<Income> =
        queries.getIncomesByDateRange(startDate.toLong(), endDate.toLong())
            .executeAsList()
}