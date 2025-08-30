package com.andef.myfinance.core.domain.expense_category.usecases

import com.andef.myfinance.core.domain.expense_category.repository.ExpenseCategoryRepository

class DeleteExpenseCategoryUseCase(private val repository: ExpenseCategoryRepository) {
    suspend operator fun invoke(id: Long) = repository.deleteExpenseCategory(id)
}