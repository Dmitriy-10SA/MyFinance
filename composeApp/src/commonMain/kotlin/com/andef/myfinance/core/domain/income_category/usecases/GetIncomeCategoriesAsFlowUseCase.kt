package com.andef.myfinance.core.domain.income_category.usecases

import com.andef.myfinance.core.domain.income_category.entities.IncomeCategory
import com.andef.myfinance.core.domain.income_category.repository.IncomeCategoryRepository
import kotlinx.coroutines.flow.Flow

class GetIncomeCategoriesAsFlowUseCase(private val repository: IncomeCategoryRepository) {
    operator fun invoke(): Flow<List<IncomeCategory>> = repository.getIncomeCategoriesAsFlow()
}