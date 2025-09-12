package com.andef.myfinance.core.domain.expense_common.expense.usecases

import com.andef.myfinance.core.domain.expense_common.expense.repository.ExpenseRepository

class DeleteAllExpensesByCategoryUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(category: String) =
        repository.deleteAllExpensesByCategory(category)
}