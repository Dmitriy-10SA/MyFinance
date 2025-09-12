package com.andef.myfinance.feature.income_common.income_category.di

import com.andef.myfinance.feature.income_common.income_category.presentation.IncomeCategoryAddViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val incomeCategoryAddViewModelModule = module {
    viewModelOf(::IncomeCategoryAddViewModel)
}