package com.andef.myfinance.domain.usecases.income

import androidx.lifecycle.LiveData
import com.andef.myfinance.domain.entities.Date
import com.andef.myfinance.domain.repository.income.IncomeRepository
import javax.inject.Inject

class GetFullIncomeByDayUseCase @Inject constructor(
    private val repository: IncomeRepository
) {
    fun execute(date: Date): LiveData<Double> {
        return repository.getFullIncomeByDay(date)
    }
}