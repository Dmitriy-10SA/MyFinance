package com.andef.myfinance.core.data.income_category.repository

import com.andef.myfinance.core.data.income_category.dao.IncomeCategoryDao
import com.andef.myfinance.core.data.income_category.mapper.IncomeCategoryMapper
import com.andef.myfinance.core.domain.income_category.entities.IncomeCategoryModel
import com.andef.myfinance.core.domain.income_category.repository.IncomeCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class IncomeCategoryRepositoryImpl(
    private val dao: IncomeCategoryDao,
    private val mapper: IncomeCategoryMapper
) : IncomeCategoryRepository {
    override suspend fun addIncomeCategory(incomeCategoryModel: IncomeCategoryModel) {
        dao.addIncomeCategory(mapper.toDbo(incomeCategoryModel))
    }

    override suspend fun changeIncomeCategory(id: Long, title: String) {
        dao.changeIncomeCategory(id, title)
    }

    override suspend fun deleteIncomeCategory(id: Long) {
        dao.deleteIncomeCategory(id)
    }

    override suspend fun getIncomeCategories(): List<IncomeCategoryModel> {
        return dao.getIncomeCategories().map { mapper.toDomain(it) }
    }

    override fun getIncomeCategoriesAsFlow(): Flow<List<IncomeCategoryModel>> {
        return dao.getIncomeCategoriesAsFlow().map {
            it.map { mapper.toDomain(it) }
        }
    }
}