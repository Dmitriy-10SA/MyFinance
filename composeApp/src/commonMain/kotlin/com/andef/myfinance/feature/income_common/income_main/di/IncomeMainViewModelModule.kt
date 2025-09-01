package com.andef.myfinance.feature.income_common.income_main.di

import com.andef.myfinance.feature.income_common.income_main.presentation.IncomeMainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val incomeMainViewModelModule = module {
    viewModelOf(::IncomeMainViewModel)
}