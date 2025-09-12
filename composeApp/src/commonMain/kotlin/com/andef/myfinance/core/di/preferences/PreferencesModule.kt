package com.andef.myfinance.core.di.preferences

import com.andef.myfinance.core.data.preferences.repository.PreferencesRepositoryImpl
import com.andef.myfinance.core.domain.preferences.repository.PreferencesRepository
import com.andef.myfinance.core.domain.preferences.usecases.GetIsFirstStartUseCase
import com.andef.myfinance.core.domain.preferences.usecases.GetIsLightThemeAsFlowUseCase
import com.andef.myfinance.core.domain.preferences.usecases.GetIsLightThemeUseCase
import com.andef.myfinance.core.domain.preferences.usecases.GetUsernameAsFlowUseCase
import com.andef.myfinance.core.domain.preferences.usecases.GetUsernameUseCase
import com.andef.myfinance.core.domain.preferences.usecases.SetIsFirstStartUseCase
import com.andef.myfinance.core.domain.preferences.usecases.SetIsLightThemeUseCase
import com.andef.myfinance.core.domain.preferences.usecases.SetUsernameUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect fun settingsModule(): Module

private val preferencesRepositoryModule = module {
    singleOf(::PreferencesRepositoryImpl).bind<PreferencesRepository>()
    factoryOf(::GetIsFirstStartUseCase)
    factoryOf(::GetIsLightThemeAsFlowUseCase)
    factoryOf(::GetIsLightThemeUseCase)
    factoryOf(::GetUsernameUseCase)
    factoryOf(::GetUsernameAsFlowUseCase)
    factoryOf(::SetIsFirstStartUseCase)
    factoryOf(::SetIsLightThemeUseCase)
    factoryOf(::SetUsernameUseCase)
}

val preferencesModule = listOf(settingsModule(), preferencesRepositoryModule)