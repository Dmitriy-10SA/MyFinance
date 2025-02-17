package com.andef.myfinance.domain.repository.income

import androidx.lifecycle.LiveData
import com.andef.myfinance.domain.entities.Date
import com.andef.myfinance.domain.entities.ExpenseItem
import com.andef.myfinance.domain.entities.IncomeItem

interface IncomeRepository {
    fun getIncomesByDay(date: Date): LiveData<List<IncomeItem>>
    fun getIncomesByPeriod(startDate: Date, endDate: Date): LiveData<List<IncomeItem>>
    suspend fun addIncome(incomeItem: IncomeItem)
    suspend fun removeIncome(id: Int)
}