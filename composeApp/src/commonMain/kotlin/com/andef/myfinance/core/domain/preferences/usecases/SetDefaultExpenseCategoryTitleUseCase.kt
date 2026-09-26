package com.andef.myfinance.core.domain.preferences.usecases

import com.andef.myfinance.core.domain.preferences.repository.PreferencesRepository

class SetDefaultExpenseCategoryTitleUseCase(private val repository: PreferencesRepository) {
    operator fun invoke(title: String?) = repository.setDefaultExpenseCategoryTitle(title)
}
