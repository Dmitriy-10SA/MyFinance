package com.andef.myfinance.core.domain.expense_common.expense.usecases

import com.andef.myfinance.core.domain.expense_common.expense.entities.ExpenseModel
import com.andef.myfinance.core.domain.expense_common.expense.repository.ExpenseRepository

class AddExpenseUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(expenseModel: ExpenseModel) = repository.addExpense(expenseModel)
}