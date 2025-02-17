package com.andef.myfinance.domain.usecases.income

import androidx.lifecycle.LiveData
import com.andef.myfinance.domain.entities.Date
import com.andef.myfinance.domain.entities.ExpenseItem
import com.andef.myfinance.domain.entities.IncomeItem
import com.andef.myfinance.domain.repository.expense.ExpenseRepository
import com.andef.myfinance.domain.repository.income.IncomeRepository
import javax.inject.Inject

class GetIncomesByDayUseCase @Inject constructor(
    private val repository: IncomeRepository
) {
    fun execute(date: Date): LiveData<List<IncomeItem>> {
        return repository.getIncomesByDay(date)
    }
}