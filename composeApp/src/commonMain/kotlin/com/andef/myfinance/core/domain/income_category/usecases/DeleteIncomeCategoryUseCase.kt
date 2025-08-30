package com.andef.myfinance.core.domain.income_category.usecases

import com.andef.myfinance.core.domain.income_category.repository.IncomeCategoryRepository

class DeleteIncomeCategoryUseCase(private val repository: IncomeCategoryRepository) {
    suspend operator fun invoke(id: Long) = repository.deleteIncomeCategory(id)
}