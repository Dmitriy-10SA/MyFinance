package com.andef.myfinance.core.domain.income_category.repository

import com.andef.myfinance.core.domain.income_category.entities.IncomeCategory
import kotlinx.coroutines.flow.Flow

interface IncomeCategoryRepository {
    suspend fun addIncomeCategory(incomeCategory: IncomeCategory): Long
    suspend fun changeIncomeCategory(id: Long, title: String)
    suspend fun deleteIncomeCategory(id: Long)
    suspend fun getIncomeCategories(): List<IncomeCategory>
    fun getIncomeCategoriesAsFlow(): Flow<List<IncomeCategory>>
}