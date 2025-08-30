package com.andef.myfinance.core.domain.income.usecases

import com.andef.myfinance.core.domain.income.entities.Income
import com.andef.myfinance.core.domain.income.repository.IncomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

class GetIncomesByDateRangeFlowUseCase(private val repository: IncomeRepository) {
    operator fun invoke(startDate: LocalDate, endDate: LocalDate): Flow<List<Income>> =
        repository.getIncomesByDateRangeFlow(startDate, endDate)
}