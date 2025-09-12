package com.andef.myfinance.core.domain.preferences.usecases

import com.andef.myfinance.core.domain.preferences.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetIsLightThemeAsFlowUseCase(private val repository: PreferencesRepository) {
    operator fun invoke(): Flow<Boolean> = repository.getIsLightThemeAsFlow()
}