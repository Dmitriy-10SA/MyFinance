package com.andef.myfinance.feature.currency.domain.usecases

import com.andef.myfinance.feature.currency.domain.entities.CurrencyRub
import com.andef.myfinance.feature.currency.domain.repository.CurrencyRepository
import kotlinx.datetime.LocalDate

class GetGbpRubUseCase(private val repository: CurrencyRepository) {
    suspend operator fun invoke(): CurrencyRub.Gbp = repository.getGbpRub()
    suspend operator fun invoke(date: LocalDate): CurrencyRub.Gbp = repository.getGbpRub(date)
}