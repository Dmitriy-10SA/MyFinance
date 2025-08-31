package com.andef.myfinance.core.domain.income_common.income.usecases

import com.andef.myfinance.core.domain.income_common.income.entities.IncomeModel
import com.andef.myfinance.core.domain.income_common.income.repository.IncomeRepository
import kotlinx.datetime.LocalDate

class GetIncomesByDateRangeUseCase(private val repository: IncomeRepository) {
    suspend operator fun invoke(startDate: LocalDate, endDate: LocalDate): List<IncomeModel> =
        repository.getIncomesByDateRange(startDate, endDate)
}