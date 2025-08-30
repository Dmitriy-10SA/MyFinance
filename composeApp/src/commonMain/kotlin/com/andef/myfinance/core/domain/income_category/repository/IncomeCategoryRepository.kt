package com.andef.myfinance.core.domain.income_category.repository

import com.andef.myfinance.core.domain.income_category.entities.IncomeCategoryModel
import kotlinx.coroutines.flow.Flow

interface IncomeCategoryRepository {
    suspend fun addIncomeCategory(incomeCategoryModel: IncomeCategoryModel)
    suspend fun changeIncomeCategory(id: Long, title: String)
    suspend fun deleteIncomeCategory(id: Long)
    suspend fun getIncomeCategories(): List<IncomeCategoryModel>
    fun getIncomeCategoriesAsFlow(): Flow<List<IncomeCategoryModel>>
}