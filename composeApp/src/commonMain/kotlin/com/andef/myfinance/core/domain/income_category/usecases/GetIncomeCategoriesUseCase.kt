package com.andef.myfinance.core.domain.income_category.usecases

import com.andef.myfinance.core.domain.income_category.repository.IncomeCategoryRepository

class GetIncomeCategoriesUseCase (private val repository: IncomeCategoryRepository) {
    suspend operator fun invoke() = repository.getIncomeCategories()
}