package com.andef.myfinance.feature.expense_category.domain.usecases

import com.andef.myfinance.core.domain.expense.repository.ExpenseRepository

class DeleteAllExpensesByCategoryUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(category: String) =
        repository.deleteAllExpensesByCategory(category)
}