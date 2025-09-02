package com.andef.myfinance.feature.income_common.income_analysis.di

import com.andef.myfinance.feature.income_common.income_analysis.presentation.IncomeAnalysisViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val incomeAnalysisViewModelModule = module {
    viewModelOf(::IncomeAnalysisViewModel)
}