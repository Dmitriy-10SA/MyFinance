package com.andef.myfinance.domain.usecases.income

import com.andef.myfinance.domain.repository.income.IncomeRepository
import javax.inject.Inject

class RemoveIncomeUseCase @Inject constructor(
    private val repository: IncomeRepository
) {
    suspend fun execute(id: Int) {
        repository.removeIncome(id)
    }
}