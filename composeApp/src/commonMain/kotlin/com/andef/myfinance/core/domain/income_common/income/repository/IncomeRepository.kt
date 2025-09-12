package com.andef.myfinance.core.domain.income_common.income.repository

import com.andef.myfinance.core.domain.income_common.income.entities.IncomeModel
import com.andef.myfinance.core.domain.income_common.income_category.entities.IncomeCategoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface IncomeRepository {
    suspend fun getIncomeById(id: Long): IncomeModel
    suspend fun addIncome(incomeModel: IncomeModel)
    suspend fun updateIncome(
        id: Long,
        amount: Double,
        category: IncomeCategoryModel,
        date: LocalDate,
        note: String?
    )

    suspend fun changeAllIncomeCategoryByOldCategory(old: String, new: String)
    suspend fun deleteAllIncomesByCategory(category: String)
    suspend fun deleteIncome(id: Long)
    fun getIncomesByDateRangeFlow(startDate: LocalDate, endDate: LocalDate): Flow<List<IncomeModel>>
    suspend fun getIncomesByDateRange(startDate: LocalDate, endDate: LocalDate): List<IncomeModel>
}