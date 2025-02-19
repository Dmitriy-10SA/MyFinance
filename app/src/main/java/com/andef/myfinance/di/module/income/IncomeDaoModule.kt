package com.andef.myfinance.di.module.income

import android.app.Application
import com.andef.myfinance.data.dao.expense.IncomesDao
import com.andef.myfinance.data.database.income.IncomesDataBase
import dagger.Module
import dagger.Provides

@Module
class IncomeDaoModule {
    @Provides
    fun provideIncomeDao(application: Application): IncomesDao {
        return IncomesDataBase.getInstance(application).incomesDao
    }
}