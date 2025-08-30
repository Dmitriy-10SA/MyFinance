package com.andef.myfinance.core.domain.income.usecases

import com.andef.myfinance.core.domain.income.entities.Income
import com.andef.myfinance.core.domain.income.repository.IncomeRepository
import kotlinx.datetime.LocalDate

class GetIncomesByDateRangeUseCase(private val repository: IncomeRepository) {
    suspend operator fun invoke(startDate: LocalDate, endDate: LocalDate): List<Income> =
        repository.getIncomesByDateRange(startDate, endDate)
}