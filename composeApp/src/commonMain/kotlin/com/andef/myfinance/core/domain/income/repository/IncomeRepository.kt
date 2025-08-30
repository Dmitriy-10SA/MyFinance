package com.andef.myfinance.core.domain.income.repository

import com.andef.myfinance.core.domain.income.entities.Income
import com.andef.myfinance.core.domain.income_category.entities.IncomeCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface IncomeRepository {
    suspend fun getIncomeById(id: Long): Income
    suspend fun addIncome(income: Income)
    suspend fun updateIncome(
        id: Long,
        amount: Double,
        category: IncomeCategory,
        date: LocalDate,
        note: String?
    )

    suspend fun changeAllIncomeCategoryByOldCategory(old: String, new: String)
    suspend fun deleteAllIncomesByCategory(category: String)
    suspend fun deleteIncome(id: Long)
    fun getIncomesByDateRangeFlow(startDate: LocalDate, endDate: LocalDate): Flow<List<Income>>
    suspend fun getIncomesByDateRange(startDate: LocalDate, endDate: LocalDate): List<Income>
}