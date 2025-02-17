package com.andef.myfinance.di.module.expense

import android.app.Application
import com.andef.myfinance.data.dao.income.ExpenseDao
import com.andef.myfinance.data.database.expense.ExpensesDataBase
import dagger.Module
import dagger.Provides

@Module
class ExpenseDaoModule {
    @Provides
    fun provideExpenseDao(application: Application): ExpenseDao {
        return ExpensesDataBase.getInstance(application).expenseDao
    }
}