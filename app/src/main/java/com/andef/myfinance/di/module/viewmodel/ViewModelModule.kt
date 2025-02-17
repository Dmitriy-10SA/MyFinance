package com.andef.myfinance.di.module.viewmodel

import androidx.lifecycle.ViewModel
import com.andef.myfinance.di.annotation.ViewModelKey
import com.andef.myfinance.presentation.viewmodel.ExpensesViewModel
import com.andef.myfinance.presentation.viewmodel.IncomesViewModel
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
}