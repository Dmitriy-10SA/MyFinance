package com.andef.myfinance.domain.usecases.expense

import androidx.lifecycle.LiveData
import com.andef.myfinance.domain.entities.Date
import com.andef.myfinance.domain.entities.ExpenseItem
import com.andef.myfinance.domain.repository.expense.ExpenseRepository
import javax.inject.Inject

class GetExpensesByPeriodUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    fun execute(startDate: Date, endDate: Date): LiveData<List<ExpenseItem>> {
        return repository.getExpensesByPeriod(startDate, endDate)
    }
}