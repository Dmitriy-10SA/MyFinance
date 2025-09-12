package com.andef.myfinance.core.domain.income_common.income_category.usecases

import com.andef.myfinance.core.domain.income_common.income_category.entities.IncomeCategoryModel
import com.andef.myfinance.core.domain.income_common.income_category.repository.IncomeCategoryRepository
import kotlinx.coroutines.flow.Flow

class GetIncomeCategoriesAsFlowUseCase(private val repository: IncomeCategoryRepository) {
    operator fun invoke(): Flow<List<IncomeCategoryModel>> = repository.getIncomeCategoriesAsFlow()
}