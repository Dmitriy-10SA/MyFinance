package com.andef.myfinance.core.data.expense_common.expense.repository

import com.andef.myfinance.core.data.expense_common.expense.dao.ExpenseDao
import com.andef.myfinance.core.data.expense_common.expense.mapper.ExpenseMapper
import com.andef.myfinance.core.domain.expense_common.expense.entities.ExpenseModel
import com.andef.myfinance.core.domain.expense_common.expense.repository.ExpenseRepository
import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel
import com.andef.myfinance.core.utils.mappers.localdate.localDateToInt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class ExpenseRepositoryImpl(
    private val dao: ExpenseDao,
    private val mapper: ExpenseMapper
) : ExpenseRepository {
    override suspend fun getExpenseById(id: Long): ExpenseModel {
        return mapper.toEntity(dao.getExpenseById(id))
    }

    override suspend fun addExpense(expenseModel: ExpenseModel) {
        dao.insert(mapper.fromEntity(expenseModel))
    }

    override suspend fun updateExpense(
        id: Long,
        amount: Double,
        category: ExpenseCategoryModel,
        date: LocalDate,
        note: String?
    ) {
        dao.update(
            mapper.fromEntity(
                ExpenseModel(
                    id = id,
                    amount = amount,
                    category = category,
                    date = date,
                    note = note
                )
            )
        )
    }

    override suspend fun changeAllExpenseCategoryByOldCategory(
        old: String,
        new: String
    ) {
        dao.changeAllExpenseCategoryByOldCategory(old, new)
    }

    override suspend fun deleteAllExpensesByCategory(category: String) {
        dao.deleteAllExpensesByCategory(category)
    }

    override suspend fun deleteExpense(id: Long) {
        dao.deleteById(id)
    }

    override fun getExpensesByDateRangeFlow(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<ExpenseModel>> {
        return dao.getExpensesByDateRangeFlow(localDateToInt(startDate), localDateToInt(endDate))
            .map { dbos -> dbos.map { mapper.toEntity(it) } }
    }

    override suspend fun getExpensesByDateRange(
        startDate: LocalDate,
        endDate: LocalDate
    ): List<ExpenseModel> {
        return dao.getExpensesByDateRange(localDateToInt(startDate), localDateToInt(endDate))
            .map { mapper.toEntity(it) }
    }
}