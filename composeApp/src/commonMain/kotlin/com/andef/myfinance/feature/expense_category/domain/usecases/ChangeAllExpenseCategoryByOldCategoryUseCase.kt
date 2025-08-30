package com.andef.myfinance.feature.expense_category.domain.usecases

import com.andef.myfinance.core.domain.expense.repository.ExpenseRepository

class ChangeAllExpenseCategoryByOldCategoryUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(old: String, new: String) =
        repository.changeAllExpenseCategoryByOldCategory(old, new)
}