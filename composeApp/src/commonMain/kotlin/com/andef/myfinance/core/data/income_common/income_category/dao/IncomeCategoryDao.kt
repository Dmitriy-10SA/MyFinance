package com.andef.myfinance.core.data.income_common.income_category.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.andef.myfinance.db.IncomeCategoryQueries
import com.andef.myfinance.db.Income_category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class IncomeCategoryDao(private val query: IncomeCategoryQueries) {
    fun addIncomeCategory(incomeCategoryDbo: Income_category) =
        query.addIncomeCategory(incomeCategoryDbo.title)

    fun changeIncomeCategory(id: Long, title: String) = query.changeIncomeCategory(title, id)

    fun deleteIncomeCategory(id: Long) = query.deleteIncomeCategory(id)

    fun getIncomeCategories(): List<Income_category> = query.getIncomeCategories().executeAsList()

    fun getIncomeCategoriesAsFlow(): Flow<List<Income_category>> =
        query.getIncomeCategories()
            .asFlow()
            .flowOn(Dispatchers.IO)
            .mapToList(Dispatchers.IO)
}