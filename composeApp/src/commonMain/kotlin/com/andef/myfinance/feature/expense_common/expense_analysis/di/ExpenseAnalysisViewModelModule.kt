package com.andef.myfinance.feature.expense_common.expense_analysis.di

import com.andef.myfinance.feature.expense_common.expense_analysis.presentation.ExpenseAnalysisViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val expenseAnalysisViewModelModule = module {
    viewModelOf(::ExpenseAnalysisViewModel)
}