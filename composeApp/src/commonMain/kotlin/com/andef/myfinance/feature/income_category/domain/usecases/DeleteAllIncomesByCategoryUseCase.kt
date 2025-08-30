package com.andef.myfinance.feature.income_category.domain.usecases

import com.andef.myfinance.core.domain.income.repository.IncomeRepository

class DeleteAllIncomesByCategoryUseCase(private val repository: IncomeRepository) {
    suspend operator fun invoke(category: String) =
        repository.deleteAllIncomesByCategory(category)
}