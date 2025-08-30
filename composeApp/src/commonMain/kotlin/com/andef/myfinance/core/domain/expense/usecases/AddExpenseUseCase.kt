package com.andef.myfinance.core.domain.expense.usecases

import com.andef.myfinance.core.domain.expense.entities.ExpenseModel
import com.andef.myfinance.core.domain.expense.repository.ExpenseRepository

class AddExpenseUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(expenseModel: ExpenseModel) = repository.addExpense(expenseModel)
}