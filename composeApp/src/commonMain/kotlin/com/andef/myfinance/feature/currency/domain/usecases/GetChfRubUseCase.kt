package com.andef.myfinance.feature.currency.domain.usecases

import com.andef.myfinance.feature.currency.domain.entities.CurrencyRub
import com.andef.myfinance.feature.currency.domain.repository.CurrencyRepository
import kotlinx.datetime.LocalDate

class GetChfRubUseCase(private val repository: CurrencyRepository) {
    suspend operator fun invoke(): CurrencyRub.Chf = repository.getChfRub()
    suspend operator fun invoke(date: LocalDate): CurrencyRub.Chf = repository.getChfRub(date)
}