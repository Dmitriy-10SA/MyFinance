package com.andef.myfinance.feature.auth.di

import com.andef.myfinance.feature.auth.presentation.AuthViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authViewModelModule = module {
    viewModelOf(::AuthViewModel)
}