package com.andef.myfinance.core.domain.expense.usecases

import com.andef.myfinance.core.domain.expense.entities.Expense
import com.andef.myfinance.core.domain.expense.repository.ExpenseRepository
import kotlinx.datetime.LocalDate

class GetExpensesByDateRangeUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(startDate: LocalDate, endDate: LocalDate): List<Expense> =
        repository.getExpensesByDateRange(startDate, endDate)
}