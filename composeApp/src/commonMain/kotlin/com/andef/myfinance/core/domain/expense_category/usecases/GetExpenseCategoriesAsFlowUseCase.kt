package com.andef.myfinance.core.domain.expense_category.usecases

import com.andef.myfinance.core.domain.expense_category.entities.ExpenseCategory
import com.andef.myfinance.core.domain.expense_category.repository.ExpenseCategoryRepository
import kotlinx.coroutines.flow.Flow

class GetExpenseCategoriesAsFlowUseCase(private val repository: ExpenseCategoryRepository) {
    operator fun invoke(): Flow<List<ExpenseCategory>> = repository.getExpenseCategoriesAsFlow()
}