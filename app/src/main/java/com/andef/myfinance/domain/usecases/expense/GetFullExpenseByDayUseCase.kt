package com.andef.myfinance.domain.usecases.expense

import com.andef.myfinance.domain.entities.Date
import com.andef.myfinance.domain.entities.ExpenseItem
import com.andef.myfinance.domain.repository.expense.ExpenseRepository
import javax.inject.Inject

class GetFullExpenseByDayUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    fun execute(date: Date) {
        repository.getFullExpenseByDay(date)
    }
}