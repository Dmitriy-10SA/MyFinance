package com.andef.myfinance.core.domain.expense_common.expense_category.usecases

import com.andef.myfinance.core.domain.expense_common.expense_category.repository.ExpenseCategoryRepository

class ChangeExpenseCategoryUseCase(private val repository: ExpenseCategoryRepository) {
    suspend operator fun invoke(id: Long, title: String) =
        repository.changeExpenseCategory(id, title)
}