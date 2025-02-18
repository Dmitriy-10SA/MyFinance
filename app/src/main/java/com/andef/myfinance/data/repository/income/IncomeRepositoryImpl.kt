package com.andef.myfinance.data.repository.income

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.andef.myfinance.data.dao.expense.IncomesDao
import com.andef.myfinance.data.formatter.DateFormatterWithSlash
import com.andef.myfinance.data.mapper.income.IncomeItemDbModelToIncomeItem
import com.andef.myfinance.data.mapper.income.IncomeItemToIncomeItemDbModel
import com.andef.myfinance.domain.entities.Date
import com.andef.myfinance.domain.entities.IncomeItem
import com.andef.myfinance.domain.repository.income.IncomeRepository
import javax.inject.Inject

class IncomeRepositoryImpl @Inject constructor(
    private val incomesDao: IncomesDao
) : IncomeRepository {
    override fun getIncomesByDay(date: Date): LiveData<List<IncomeItem>> {
        val formatDate = DateFormatterWithSlash.formatDate(date)
        return MediatorLiveData<List<IncomeItem>>().apply {
            addSource(incomesDao.getIncomesByDay(formatDate)) {
                value = IncomeItemDbModelToIncomeItem.mapList(it)
            }
        }
    }

    override fun getIncomesByPeriod(startDate: Date, endDate: Date): LiveData<List<IncomeItem>> {
        val formatStartDate = DateFormatterWithSlash.formatDate(startDate)
        val formatEndDate = DateFormatterWithSlash.formatDate(endDate)
        return MediatorLiveData<List<IncomeItem>>().apply {
            addSource(incomesDao.getIncomesByPeriod(formatStartDate, formatEndDate)) {
                value = IncomeItemDbModelToIncomeItem.mapList(it)
            }
        }
    }

    override suspend fun addIncome(incomeItem: IncomeItem) {
        incomesDao.addIncome(IncomeItemToIncomeItemDbModel.map(incomeItem))
    }

    override suspend fun removeIncome(id: Int) {
        incomesDao.removeIncome(id)
    }

    override fun getFullIncomeByDay(date: Date): LiveData<Double> {
        val formatDate = DateFormatterWithSlash.formatDate(date)
        return incomesDao.getFullIncomeByDay(formatDate)
    }

    override fun getFullIncomeByPeriod(startDate: Date, endDate: Date): LiveData<Double> {
        val formatStartDate = DateFormatterWithSlash.formatDate(startDate)
        val formatEndDate = DateFormatterWithSlash.formatDate(endDate)
        return incomesDao.getFullIncomeByPeriod(formatStartDate, formatEndDate)
    }
}