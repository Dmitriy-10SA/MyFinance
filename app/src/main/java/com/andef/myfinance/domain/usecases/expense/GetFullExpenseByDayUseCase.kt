package com.andef.myfinance.domain.usecases.expense

import androidx.lifecycle.LiveData
import com.andef.myfinance.domain.entities.Date
import com.andef.myfinance.domain.repository.expense.ExpenseRepository
import javax.inject.Inject

class GetFullExpenseByDayUseCase @Inject constructor(
    private val repository: ExpenseRepository
) {
    fun execute(date: Date): LiveData<Double> {
       return repository.getFullExpenseByDay(date)
    }
}