package com.andef.myfinance.feature.expense_common.expense_main.di

import com.andef.myfinance.feature.expense_common.expense_main.presentation.ExpenseMainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val expenseMainViewModelModule = module {
    viewModelOf(::ExpenseMainViewModel)
}