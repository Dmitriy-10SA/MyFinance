package com.andef.myfinance.presentation.viewmodel.finance

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andef.myfinance.domain.entities.Date
import com.andef.myfinance.domain.usecases.expense.GetFullExpenseByDayUseCase
import com.andef.myfinance.domain.usecases.expense.GetFullExpenseByPeriodUseCase
import com.andef.myfinance.domain.usecases.income.GetFullIncomeByDayUseCase
import com.andef.myfinance.domain.usecases.income.GetFullIncomeByPeriodUseCase
import javax.inject.Inject

class FinanceViewModel @Inject constructor(
    private val getFullIncomeByPeriodUseCase: GetFullIncomeByPeriodUseCase,
    private val getFullIncomeByDayUseCase: GetFullIncomeByDayUseCase,
    private val getFullExpenseByPeriodUseCase: GetFullExpenseByPeriodUseCase,
    private val getFullExpenseByDayUseCase: GetFullExpenseByDayUseCase
) : ViewModel() {
    fun getFullIncomeByDay(date: Date): LiveData<Double> {
        return getFullIncomeByDayUseCase.execute(date)
    }

    fun getFullExpenseByDay(date: Date): LiveData<Double> {
        return getFullExpenseByDayUseCase.execute(date)
    }

    fun getFullIncomeByPeriod(startDate: Date, endDate: Date): LiveData<Double> {
        return getFullIncomeByPeriodUseCase.execute(startDate, endDate)
    }

    fun getFullExpenseByPeriod(startDate: Date, endDate: Date): LiveData<Double> {
        return getFullExpenseByPeriodUseCase.execute(startDate, endDate)
    }
}