package com.andef.myfinance.domain.usecases.income

import com.andef.myfinance.domain.entities.IncomeItem
import com.andef.myfinance.domain.repository.income.IncomeRepository
import javax.inject.Inject

class AddIncomeUseCase @Inject constructor(
    private val repository: IncomeRepository
) {
    suspend fun execute(incomeItem: IncomeItem) {
        repository.addIncome(incomeItem)
    }
}