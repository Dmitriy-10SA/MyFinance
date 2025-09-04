package com.andef.myfinance.feature.expense_common.expense_category.di

import com.andef.myfinance.feature.expense_common.expense_category.presentation.ExpenseCategoryAddViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val expenseCategoryAddViewModelModule = module {
    viewModelOf(::ExpenseCategoryAddViewModel)
}