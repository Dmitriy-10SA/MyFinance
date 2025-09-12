package com.andef.myfinance.core.domain.preferences.usecases

import com.andef.myfinance.core.domain.preferences.repository.PreferencesRepository

class SetUsernameUseCase(private val repository: PreferencesRepository) {
    operator fun invoke(username: String) = repository.setUsername(username)
}