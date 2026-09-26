package com.andef.myfinance.core.data.preferences.repository

import com.andef.myfinance.core.domain.preferences.repository.PreferencesRepository
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PreferencesRepositoryImpl(private val prefs: Settings) : PreferencesRepository {
    private val isLightThemeAsFlow = MutableStateFlow(false)
    private val usernameAsFlow = MutableStateFlow(getUsername())

    override fun getIsLightTheme(isSystemInDarkTheme: Boolean): Boolean {
        val isLightTheme = prefs.getBoolean(IS_LIGHT_THEME, !isSystemInDarkTheme)
        isLightThemeAsFlow.value = isLightTheme
        return isLightTheme
    }

    override fun getIsLightThemeAsFlow(): Flow<Boolean> = isLightThemeAsFlow.asStateFlow()

    override fun setIsLightTheme(isLightTheme: Boolean) {
        prefs.putBoolean(IS_LIGHT_THEME, isLightTheme)
        isLightThemeAsFlow.value = isLightTheme
    }

    override fun getUsername(): String = prefs.getString(USERNAME_KEY, "")

    override fun getUsernameAsFlow(): Flow<String> = usernameAsFlow.asStateFlow()

    override fun setUsername(username: String) {
        prefs.putString(USERNAME_KEY, username)
        usernameAsFlow.value = username
    }

    override fun getIsFirstStart(): Boolean = prefs.getBoolean(IS_FIRST_START_KEY, true)

    override fun setIsFirstStart(isFirstStart: Boolean) {
        prefs.putBoolean(IS_FIRST_START_KEY, isFirstStart)
    }

    override fun getDefaultExpenseCategoryTitle(): String? =
        prefs.getStringOrNull(DEFAULT_EXPENSE_CATEGORY_KEY)

    override fun setDefaultExpenseCategoryTitle(title: String?) {
        if (title == null) prefs.remove(DEFAULT_EXPENSE_CATEGORY_KEY)
        else prefs.putString(DEFAULT_EXPENSE_CATEGORY_KEY, title)
    }

    override fun getDefaultIncomeCategoryTitle(): String? =
        prefs.getStringOrNull(DEFAULT_INCOME_CATEGORY_KEY)

    override fun setDefaultIncomeCategoryTitle(title: String?) {
        if (title == null) prefs.remove(DEFAULT_INCOME_CATEGORY_KEY)
        else prefs.putString(DEFAULT_INCOME_CATEGORY_KEY, title)
    }

    companion object {
        private const val IS_LIGHT_THEME = "is_light_theme"
        private const val USERNAME_KEY = "username"
        private const val IS_FIRST_START_KEY = "is_first_start"
        private const val DEFAULT_EXPENSE_CATEGORY_KEY = "default_expense_category"
        private const val DEFAULT_INCOME_CATEGORY_KEY = "default_income_category"
    }
}
