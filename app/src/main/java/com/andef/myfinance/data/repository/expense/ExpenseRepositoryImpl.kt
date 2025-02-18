package com.andef.myfinance.data.repository.expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.andef.myfinance.data.dao.income.ExpenseDao
import com.andef.myfinance.data.formatter.DateFormatterWithSlash
import com.andef.myfinance.data.mapper.expense.ExpenseItemDbModelToExpenseItem
import com.andef.myfinance.data.mapper.expense.ExpenseItemToExpenseItemDbModel
import com.andef.myfinance.domain.entities.Date
import com.andef.myfinance.domain.entities.ExpenseItem
import com.andef.myfinance.domain.repository.expense.ExpenseRepository
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao
) : ExpenseRepository {
    override fun getExpensesByDay(date: Date): LiveData<List<ExpenseItem>> {
        val formatDate = DateFormatterWithSlash.formatDate(date)
        return MediatorLiveData<List<ExpenseItem>>().apply {
            addSource(expenseDao.getExpensesByDay(formatDate)) {
                value = ExpenseItemDbModelToExpenseItem.mapList(it)
            }
        }
    }

    override fun getExpensesByPeriod(startDate: Date, endDate: Date): LiveData<List<ExpenseItem>> {
        val formatStartDate = DateFormatterWithSlash.formatDate(startDate)
        val formatEndDate = DateFormatterWithSlash.formatDate(endDate)
        return MediatorLiveData<List<ExpenseItem>>().apply {
            addSource(expenseDao.getExpensesByPeriod(formatStartDate, formatEndDate)) {
                value = ExpenseItemDbModelToExpenseItem.mapList(it)
            }
        }
    }

    override suspend fun addExpense(expenseItem: ExpenseItem) {
        expenseDao.addExpense(ExpenseItemToExpenseItemDbModel.map(expenseItem))
    }

    override suspend fun removeExpense(id: Int) {
        expenseDao.removeExpense(id)
    }

    override fun getFullExpenseByDay(date: Date): LiveData<Double> {
        val formatDate = DateFormatterWithSlash.formatDate(date)
        return expenseDao.getFullExpenseByDay(formatDate)
    }

    override fun getFullExpenseByPeriod(startDate: Date, endDate: Date): LiveData<Double> {
        val formatStartDate = DateFormatterWithSlash.formatDate(startDate)
        val formatEndDate = DateFormatterWithSlash.formatDate(endDate)
        return expenseDao.getFullExpenseByPeriod(formatStartDate, formatEndDate)
    }
}