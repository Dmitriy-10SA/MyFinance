package com.andef.myfinance.di.module.viewmodel

import androidx.lifecycle.ViewModel
import com.andef.myfinance.di.annotation.ViewModelKey
import com.andef.myfinance.presentation.viewmodel.expense.ExpensesFragmentViewModel
import com.andef.myfinance.presentation.viewmodel.expense.ExpensesViewModel
import com.andef.myfinance.presentation.viewmodel.finance.FinanceViewModel
import com.andef.myfinance.presentation.viewmodel.income.IncomesFragmentViewModel
import com.andef.myfinance.presentation.viewmodel.income.IncomesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(ExpensesViewModel::class)
    @Binds
    fun bindExpensesViewModel(impl: ExpensesViewModel): ViewModel

    @IntoMap
    @ViewModelKey(IncomesViewModel::class)
    @Binds
    fun bindIncomesViewModel(impl: IncomesViewModel): ViewModel

    @IntoMap
    @ViewModelKey(FinanceViewModel::class)
    @Binds
    fun bindFinanceViewModel(impl: FinanceViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ExpensesFragmentViewModel::class)
    @Binds
    fun bindExpensesFragmentViewModel(impl: ExpensesFragmentViewModel): ViewModel

    @IntoMap
    @ViewModelKey(IncomesFragmentViewModel::class)
    @Binds
    fun bindIncomesFragmentViewModel(impl: IncomesFragmentViewModel): ViewModel
}