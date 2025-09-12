package com.andef.myfinance.core.domain.expense_common.expense.usecases

import com.andef.myfinance.core.domain.expense_common.expense.repository.ExpenseRepository

class DeleteExpenseUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(id: Long) = repository.deleteExpense(id)
}