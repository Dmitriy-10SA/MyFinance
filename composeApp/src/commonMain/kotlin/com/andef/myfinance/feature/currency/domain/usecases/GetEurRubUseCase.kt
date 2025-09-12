package com.andef.myfinance.feature.currency.domain.usecases

import com.andef.myfinance.feature.currency.domain.entities.CurrencyRub
import com.andef.myfinance.feature.currency.domain.repository.CurrencyRepository
import kotlinx.datetime.LocalDate

class GetEurRubUseCase(private val repository: CurrencyRepository) {
    suspend operator fun invoke(): CurrencyRub.Eur = repository.getEurRub()
    suspend operator fun invoke(date: LocalDate): CurrencyRub.Eur = repository.getEurRub(date)
}