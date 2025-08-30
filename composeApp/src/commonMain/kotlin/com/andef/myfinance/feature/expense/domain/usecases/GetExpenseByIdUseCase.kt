package com.andef.myfinance.feature.expense.domain.usecases

import com.andef.myfinance.core.domain.expense.entities.ExpenseModel
import com.andef.myfinance.core.domain.expense.repository.ExpenseRepository

class GetExpenseByIdUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(id: Long): ExpenseModel = repository.getExpenseById(id)
}