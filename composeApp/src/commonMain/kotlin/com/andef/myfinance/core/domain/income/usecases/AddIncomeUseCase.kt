package com.andef.myfinance.core.domain.income.usecases

import com.andef.myfinance.core.domain.income.entities.Income
import com.andef.myfinance.core.domain.income.repository.IncomeRepository

class AddIncomeUseCase(private val repository: IncomeRepository) {
    suspend operator fun invoke(income: Income) = repository.addIncome(income)
}