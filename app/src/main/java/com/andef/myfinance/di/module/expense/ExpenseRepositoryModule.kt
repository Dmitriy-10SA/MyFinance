package com.andef.myfinance.di.module.expense

import com.andef.myfinance.data.repository.expense.ExpenseRepositoryImpl
import com.andef.myfinance.domain.repository.expense.ExpenseRepository
import dagger.Binds
import dagger.Module

@Module
interface ExpenseRepositoryModule {
    @Binds
    fun bindExpenseRepository(impl: ExpenseRepositoryImpl): ExpenseRepository
}