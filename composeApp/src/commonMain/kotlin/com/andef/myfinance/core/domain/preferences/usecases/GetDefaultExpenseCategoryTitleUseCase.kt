package com.andef.myfinance.core.domain.preferences.usecases

import com.andef.myfinance.core.domain.preferences.repository.PreferencesRepository

class GetDefaultExpenseCategoryTitleUseCase(private val repository: PreferencesRepository) {
    operator fun invoke(): String? = repository.getDefaultExpenseCategoryTitle()
}
