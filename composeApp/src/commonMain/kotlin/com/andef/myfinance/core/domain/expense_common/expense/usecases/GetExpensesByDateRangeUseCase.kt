package com.andef.myfinance.core.domain.expense_common.expense.usecases

import com.andef.myfinance.core.domain.expense_common.expense.entities.ExpenseModel
import com.andef.myfinance.core.domain.expense_common.expense.repository.ExpenseRepository
import kotlinx.datetime.LocalDate

class GetExpensesByDateRangeUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(startDate: LocalDate, endDate: LocalDate): List<ExpenseModel> =
        repository.getExpensesByDateRange(startDate, endDate)
}