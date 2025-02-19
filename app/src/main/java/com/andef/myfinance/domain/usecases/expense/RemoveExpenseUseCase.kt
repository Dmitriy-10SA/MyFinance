package com.andef.myfinance.domain.usecases.expense

import com.andef.myfinance.domain.repository.expense.ExpenseRepository
import javax.inject.Inject

class RemoveExpenseUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    suspend fun execute(id: Int) {
        repository.removeExpense(id)
    }
}