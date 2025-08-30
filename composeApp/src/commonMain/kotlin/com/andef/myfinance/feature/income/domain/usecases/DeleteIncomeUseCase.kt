package com.andef.myfinance.feature.income.domain.usecases

import com.andef.myfinance.core.domain.income.repository.IncomeRepository

class DeleteIncomeUseCase(private val repository: IncomeRepository) {
    suspend operator fun invoke(id: Long) = repository.deleteIncome(id)
}