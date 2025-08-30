package com.andef.myfinance.feature.income_category.domain.usecases

import com.andef.myfinance.core.domain.income.repository.IncomeRepository

class ChangeAllIncomeCategoryByOldCategoryUseCase(private val repository: IncomeRepository) {
    suspend operator fun invoke(old: String, new: String) =
        repository.changeAllIncomeCategoryByOldCategory(old, new)
}