package com.andef.myfinance.core.domain.income_common.income.usecases

import com.andef.myfinance.core.domain.income_common.income.entities.IncomeModel
import com.andef.myfinance.core.domain.income_common.income.repository.IncomeRepository

class GetIncomeByIdUseCase(private val repository: IncomeRepository) {
    suspend operator fun invoke(id: Long): IncomeModel = repository.getIncomeById(id)
}