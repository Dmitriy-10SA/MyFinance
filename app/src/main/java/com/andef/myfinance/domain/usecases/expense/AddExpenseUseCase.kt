package com.andef.myfinance.domain.usecases.expense

import com.andef.myfinance.domain.entities.ExpenseItem
import com.andef.myfinance.domain.repository.expense.ExpenseRepository
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    suspend fun execute(expenseItem: ExpenseItem) {
        repository.addExpense(expenseItem)
    }
}