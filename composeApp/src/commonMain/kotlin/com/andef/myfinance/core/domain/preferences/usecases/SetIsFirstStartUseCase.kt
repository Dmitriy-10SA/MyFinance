package com.andef.myfinance.core.domain.preferences.usecases

import com.andef.myfinance.core.domain.preferences.repository.PreferencesRepository

class SetIsFirstStartUseCase(private val repository: PreferencesRepository) {
    operator fun invoke(isFirstStart: Boolean) = repository.setIsFirstStart(isFirstStart)
}