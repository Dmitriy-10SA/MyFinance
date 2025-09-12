package com.andef.myfinance.core.data.expense_common.expense_category.repository

import com.andef.myfinance.core.data.expense_common.expense_category.dao.ExpenseCategoryDao
import com.andef.myfinance.core.data.expense_common.expense_category.mapper.ExpenseCategoryMapper
import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel
import com.andef.myfinance.core.domain.expense_common.expense_category.repository.ExpenseCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseCategoryRepositoryImpl(
    private val dao: ExpenseCategoryDao,
    private val mapper: ExpenseCategoryMapper
) : ExpenseCategoryRepository {
    override suspend fun addExpenseCategory(expenseCategoryModel: ExpenseCategoryModel) {
        dao.addExpenseCategory(mapper.toDbo(expenseCategoryModel))
    }

    override suspend fun changeExpenseCategory(id: Long, title: String) {
        dao.changeExpenseCategory(id, title)
    }

    override suspend fun deleteExpenseCategory(id: Long) {
        dao.deleteExpenseCategory(id)
    }

    override suspend fun getExpenseCategories(): List<ExpenseCategoryModel> {
        return dao.getExpenseCategories().map { mapper.toDomain(it) }
    }

    override fun getExpenseCategoriesAsFlow(): Flow<List<ExpenseCategoryModel>> {
        return dao.getExpenseCategoriesAsFlow().map {
            it.map { mapper.toDomain(it) }
        }
    }
}