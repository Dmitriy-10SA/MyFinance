package com.andef.myfinance.core.domain.income_common.income.usecases

import com.andef.myfinance.core.domain.income_common.income.repository.IncomeRepository

class DeleteAllIncomesByCategoryUseCase(private val repository: IncomeRepository) {
    suspend operator fun invoke(category: String) =
        repository.deleteAllIncomesByCategory(category)
}