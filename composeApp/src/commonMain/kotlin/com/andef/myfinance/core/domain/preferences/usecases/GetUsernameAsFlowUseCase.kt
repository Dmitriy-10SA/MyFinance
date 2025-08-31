package com.andef.myfinance.core.domain.preferences.usecases

import com.andef.myfinance.core.domain.preferences.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetUsernameAsFlowUseCase(private val repository: PreferencesRepository) {
    operator fun invoke(): Flow<String> = repository.getUsernameAsFlow()
}