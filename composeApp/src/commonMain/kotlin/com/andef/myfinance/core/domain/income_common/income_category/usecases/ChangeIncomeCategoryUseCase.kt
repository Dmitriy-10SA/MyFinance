package com.andef.myfinance.core.domain.income_common.income_category.usecases

import com.andef.myfinance.core.domain.income_common.income_category.repository.IncomeCategoryRepository

class ChangeIncomeCategoryUseCase(private val repository: IncomeCategoryRepository) {
    suspend operator fun invoke(id: Long, title: String) =
        repository.changeIncomeCategory(id, title)
}