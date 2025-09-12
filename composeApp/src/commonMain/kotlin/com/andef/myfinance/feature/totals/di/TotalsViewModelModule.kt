package com.andef.myfinance.feature.totals.di

import com.andef.myfinance.feature.totals.presentation.TotalsMainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val totalsViewModelModule = module {
    viewModelOf(::TotalsMainViewModel)
}