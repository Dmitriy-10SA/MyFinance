package com.andef.myfinance.feature.income_common.income_add_and_change.di

import com.andef.myfinance.feature.income_common.income_add_and_change.presentation.IncomeAddAndChangeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val incomeAddAndChangeViewModelModule = module {
    viewModelOf(::IncomeAddAndChangeViewModel)
}