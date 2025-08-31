package com.andef.myfinance.core.domain.expense_common.expense.usecases

import com.andef.myfinance.core.domain.expense_common.expense.entities.ExpenseModel
import com.andef.myfinance.core.domain.expense_common.expense.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

class GetExpensesByDateRangeFlowUseCase(private val repository: ExpenseRepository) {
    operator fun invoke(startDate: LocalDate, endDate: LocalDate): Flow<List<ExpenseModel>> =
        repository.getExpensesByDateRangeFlow(startDate, endDate)
}