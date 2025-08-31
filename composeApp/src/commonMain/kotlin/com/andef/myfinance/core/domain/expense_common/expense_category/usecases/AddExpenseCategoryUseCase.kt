package com.andef.myfinance.core.domain.expense_common.expense_category.usecases

import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel
import com.andef.myfinance.core.domain.expense_common.expense_category.repository.ExpenseCategoryRepository

class AddExpenseCategoryUseCase(private val repository: ExpenseCategoryRepository) {
    suspend operator fun invoke(expenseCategoryModel: ExpenseCategoryModel) =
        repository.addExpenseCategory(expenseCategoryModel)
}