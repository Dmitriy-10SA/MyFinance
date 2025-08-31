package com.andef.myfinance.core.domain.preferences.usecases

import com.andef.myfinance.core.domain.preferences.repository.PreferencesRepository

class SetIsLightThemeUseCase(private val repository: PreferencesRepository) {
    operator fun invoke(isLightTheme: Boolean) = repository.setIsLightTheme(isLightTheme)
}