package com.andef.myfinance.core.domain.income_common.income.usecases

import com.andef.myfinance.core.domain.income_common.income.repository.IncomeRepository

class DeleteIncomeUseCase(private val repository: IncomeRepository) {
    suspend operator fun invoke(id: Long) = repository.deleteIncome(id)
}