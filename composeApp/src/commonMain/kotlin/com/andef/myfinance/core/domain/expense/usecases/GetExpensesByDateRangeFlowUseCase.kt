package com.andef.myfinance.core.domain.expense.usecases

import com.andef.myfinance.core.domain.expense.entities.Expense
import com.andef.myfinance.core.domain.expense.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

class GetExpensesByDateRangeFlowUseCase(private val repository: ExpenseRepository) {
    operator fun invoke(startDate: LocalDate, endDate: LocalDate): Flow<List<Expense>> =
        repository.getExpensesByDateRangeFlow(startDate, endDate)
}