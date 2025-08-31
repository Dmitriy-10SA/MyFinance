package com.andef.myfinance.core.domain.expense_common.expense_category.usecases

import com.andef.myfinance.core.domain.expense_common.expense_category.repository.ExpenseCategoryRepository

class GetExpenseCategoriesUseCase (private val repository: ExpenseCategoryRepository) {
    suspend operator fun invoke() = repository.getExpenseCategories()
}