package com.andef.myfinance.feature.income.domain.usecases

import com.andef.myfinance.core.domain.income.repository.IncomeRepository
import com.andef.myfinance.core.domain.income_category.entities.IncomeCategoryModel
import kotlinx.datetime.LocalDate

class UpdateIncomeUseCase(private val repository: IncomeRepository) {
    suspend operator fun invoke(
        id: Long,
        amount: Double,
        category: IncomeCategoryModel,
        date: LocalDate,
        note: String?
    ) = repository.updateIncome(id, amount, category, date, note)
}