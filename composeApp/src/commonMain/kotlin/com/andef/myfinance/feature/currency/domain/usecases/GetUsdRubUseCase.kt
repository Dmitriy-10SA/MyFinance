package com.andef.myfinance.feature.currency.domain.usecases

import com.andef.myfinance.feature.currency.domain.entities.CurrencyRub
import com.andef.myfinance.feature.currency.domain.repository.CurrencyRepository
import kotlinx.datetime.LocalDate

class GetUsdRubUseCase(private val repository: CurrencyRepository) {
    suspend operator fun invoke(): CurrencyRub.Usd = repository.getUsdRub()
    suspend operator fun invoke(date: LocalDate): CurrencyRub.Usd = repository.getUsdRub(date)
}