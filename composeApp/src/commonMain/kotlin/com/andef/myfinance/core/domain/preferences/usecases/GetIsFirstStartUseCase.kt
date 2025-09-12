package com.andef.myfinance.core.domain.preferences.usecases

import com.andef.myfinance.core.domain.preferences.repository.PreferencesRepository

class GetIsFirstStartUseCase(private val repository: PreferencesRepository) {
    operator fun invoke(): Boolean = repository.getIsFirstStart()
}