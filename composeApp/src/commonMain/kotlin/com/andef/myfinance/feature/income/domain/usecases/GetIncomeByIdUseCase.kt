package com.andef.myfinance.feature.income.domain.usecases

import com.andef.myfinance.core.domain.income.entities.IncomeModel
import com.andef.myfinance.core.domain.income.repository.IncomeRepository

class GetIncomeByIdUseCase(private val repository: IncomeRepository) {
    suspend operator fun invoke(id: Long): IncomeModel = repository.getIncomeById(id)
}