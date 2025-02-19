package com.andef.myfinance.domain.usecases.income

import androidx.lifecycle.LiveData
import com.andef.myfinance.domain.entities.Date
import com.andef.myfinance.domain.entities.IncomeItem
import com.andef.myfinance.domain.repository.income.IncomeRepository
import javax.inject.Inject

class GetIncomesByPeriodUseCase @Inject constructor(
    private val repository: IncomeRepository
) {
    fun execute(startDate: Date, endDate: Date): LiveData<List<IncomeItem>> {
        return repository.getIncomesByPeriod(startDate, endDate)
    }
}