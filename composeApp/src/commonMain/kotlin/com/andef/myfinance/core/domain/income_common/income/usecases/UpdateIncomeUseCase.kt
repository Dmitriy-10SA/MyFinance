package com.andef.myfinance.core.domain.income_common.income.usecases

import com.andef.myfinance.core.domain.income_common.income.repository.IncomeRepository
import com.andef.myfinance.core.domain.income_common.income_category.entities.IncomeCategoryModel
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