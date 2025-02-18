package com.andef.myfinance.presentation.viewmodel.income

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andef.myfinance.domain.entities.Date
import com.andef.myfinance.domain.entities.IncomeItem
import com.andef.myfinance.domain.usecases.income.GetIncomesByDayUseCase
import com.andef.myfinance.domain.usecases.income.GetIncomesByPeriodUseCase
import javax.inject.Inject

class IncomesFragmentViewModel @Inject constructor(
    private val getIncomesByDayUseCase: GetIncomesByDayUseCase,
    private val getIncomesByPeriodUseCase: GetIncomesByPeriodUseCase
) : ViewModel() {
    fun getIncomesByDay(date: Date): LiveData<List<IncomeItem>> {
        return getIncomesByDayUseCase.execute(date)
    }

    fun getIncomesByPeriod(startDate: Date, endDate: Date): LiveData<List<IncomeItem>> {
        return getIncomesByPeriodUseCase.execute(startDate, endDate)
    }
}