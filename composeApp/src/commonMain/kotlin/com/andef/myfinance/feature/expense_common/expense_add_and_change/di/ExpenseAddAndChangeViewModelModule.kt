package com.andef.myfinance.feature.expense_common.expense_add_and_change.di

import com.andef.myfinance.feature.expense_common.expense_add_and_change.presentation.ExpenseAddAndChangeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val expenseAddAndChangeViewModelModule = module {
    viewModelOf(::ExpenseAddAndChangeViewModel)
}