package com.andef.myfinance.feature.income.domain.usecases

import com.andef.myfinance.core.domain.income.entities.Income
import com.andef.myfinance.core.domain.income.repository.IncomeRepository

class GetIncomeByIdUseCase(private val repository: IncomeRepository) {
    suspend operator fun invoke(id: Long): Income = repository.getIncomeById(id)
}