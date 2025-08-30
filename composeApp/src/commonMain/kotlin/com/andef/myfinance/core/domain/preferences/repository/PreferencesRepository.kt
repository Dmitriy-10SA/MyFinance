package com.andef.myfinance.core.domain.preferences.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun getIsLightTheme(isSystemInDarkTheme: Boolean): Boolean
    fun getIsLightThemeAsFlow(): Flow<Boolean>
    fun setIsLightTheme(isLightTheme: Boolean)
    fun getUsername(): String
    fun getUsernameAsFlow(): Flow<String>
    fun setUsername(username: String)
    fun getIsFirstStart(): Boolean
    fun setIsFirstStart(isFirstStart: Boolean)
}