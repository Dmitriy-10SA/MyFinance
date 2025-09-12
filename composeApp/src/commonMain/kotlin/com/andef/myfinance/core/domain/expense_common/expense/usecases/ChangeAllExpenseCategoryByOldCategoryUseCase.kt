package com.andef.myfinance.core.domain.expense_common.expense.usecases

import com.andef.myfinance.core.domain.expense_common.expense.repository.ExpenseRepository

class ChangeAllExpenseCategoryByOldCategoryUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(old: String, new: String) =
        repository.changeAllExpenseCategoryByOldCategory(old, new)
}