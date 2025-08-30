package com.andef.myfinance.core.domain.expense.usecases

import com.andef.myfinance.core.domain.expense.entities.Expense
import com.andef.myfinance.core.domain.expense.repository.ExpenseRepository

class AddExpenseUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(expense: Expense) = repository.addExpense(expense)
}