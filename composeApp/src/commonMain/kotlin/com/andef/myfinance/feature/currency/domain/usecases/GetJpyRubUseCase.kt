package com.andef.myfinance.feature.currency.domain.usecases

import com.andef.myfinance.feature.currency.domain.entities.CurrencyRub
import com.andef.myfinance.feature.currency.domain.repository.CurrencyRepository
import kotlinx.datetime.LocalDate

class GetJpyRubUseCase(private val repository: CurrencyRepository) {
    suspend operator fun invoke(): CurrencyRub.Jpy = repository.getJpyRub()
    suspend operator fun invoke(date: LocalDate): CurrencyRub.Jpy = repository.getJpyRub(date)
}