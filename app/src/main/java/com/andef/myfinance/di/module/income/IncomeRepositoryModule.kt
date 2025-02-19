package com.andef.myfinance.di.module.income

import com.andef.myfinance.data.repository.income.IncomeRepositoryImpl
import com.andef.myfinance.domain.repository.income.IncomeRepository
import dagger.Binds
import dagger.Module

@Module
interface IncomeRepositoryModule {
    @Binds
    fun bindIncomeRepository(impl: IncomeRepositoryImpl): IncomeRepository
}