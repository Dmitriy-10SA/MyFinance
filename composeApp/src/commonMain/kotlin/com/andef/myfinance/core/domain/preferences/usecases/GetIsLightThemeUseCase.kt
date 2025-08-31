package com.andef.myfinance.core.domain.preferences.usecases

import com.andef.myfinance.core.domain.preferences.repository.PreferencesRepository

class GetIsLightThemeUseCase(private val repository: PreferencesRepository) {
    operator fun invoke(isSystemInDarkTheme: Boolean): Boolean =
        repository.getIsLightTheme(isSystemInDarkTheme)
}