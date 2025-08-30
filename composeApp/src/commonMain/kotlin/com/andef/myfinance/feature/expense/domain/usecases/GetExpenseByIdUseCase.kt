package com.andef.myfinance.feature.expense.domain.usecases

import com.andef.myfinance.core.domain.expense.entities.Expense
import com.andef.myfinance.core.domain.expense.repository.ExpenseRepository

class GetExpenseByIdUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(id: Long): Expense = repository.getExpenseById(id)
}