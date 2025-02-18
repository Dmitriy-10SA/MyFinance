package com.andef.myfinance.domain.usecases.expense

import com.andef.myfinance.domain.entities.Date
import com.andef.myfinance.domain.repository.expense.ExpenseRepository
import javax.inject.Inject

class GetFullExpenseByPeriodUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    fun execute(startDate: Date, endDate: Date) {
        repository.getFullExpenseByPeriod(startDate, endDate)
    }
}