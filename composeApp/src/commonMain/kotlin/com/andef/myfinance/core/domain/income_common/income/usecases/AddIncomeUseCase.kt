package com.andef.myfinance.core.domain.income_common.income.usecases

import com.andef.myfinance.core.domain.income_common.income.entities.IncomeModel
import com.andef.myfinance.core.domain.income_common.income.repository.IncomeRepository

class AddIncomeUseCase(private val repository: IncomeRepository) {
    suspend operator fun invoke(incomeModel: IncomeModel) = repository.addIncome(incomeModel)
}