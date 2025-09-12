package com.andef.myfinance.core.domain.expense_common.expense_category.usecases

import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel
import com.andef.myfinance.core.domain.expense_common.expense_category.repository.ExpenseCategoryRepository
import kotlinx.coroutines.flow.Flow

class GetExpenseCategoriesAsFlowUseCase(private val repository: ExpenseCategoryRepository) {
    operator fun invoke(): Flow<List<ExpenseCategoryModel>> = repository.getExpenseCategoriesAsFlow()
}