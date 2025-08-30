package com.andef.myfinance.core.domain.income.usecases

import com.andef.myfinance.core.domain.income.entities.IncomeModel
import com.andef.myfinance.core.domain.income.repository.IncomeRepository

class AddIncomeUseCase(private val repository: IncomeRepository) {
    suspend operator fun invoke(incomeModel: IncomeModel) = repository.addIncome(incomeModel)
}