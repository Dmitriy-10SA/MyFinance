package com.andef.myfinance.feature.expense.domain.usecases

import com.andef.myfinance.core.domain.expense.repository.ExpenseRepository

class DeleteExpenseUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(id: Long) = repository.deleteExpense(id)
}


