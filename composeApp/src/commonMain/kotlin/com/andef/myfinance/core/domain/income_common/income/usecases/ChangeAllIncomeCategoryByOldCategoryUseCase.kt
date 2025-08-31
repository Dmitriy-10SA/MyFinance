package com.andef.myfinance.core.domain.income_common.income.usecases

import com.andef.myfinance.core.domain.income_common.income.repository.IncomeRepository

class ChangeAllIncomeCategoryByOldCategoryUseCase(private val repository: IncomeRepository) {
    suspend operator fun invoke(old: String, new: String) =
        repository.changeAllIncomeCategoryByOldCategory(old, new)
}