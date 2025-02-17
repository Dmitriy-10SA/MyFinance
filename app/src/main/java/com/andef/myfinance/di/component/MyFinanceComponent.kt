package com.andef.myfinance.di.component

import android.app.Application
import com.andef.myfinance.di.annotation.ApplicationScope
import com.andef.myfinance.di.module.expense.ExpenseDaoModule
import com.andef.myfinance.di.module.expense.ExpenseRepositoryModule
import com.andef.myfinance.di.module.income.IncomeDaoModule
import com.andef.myfinance.di.module.income.IncomeRepositoryModule
import com.andef.myfinance.di.module.viewmodel.ViewModelModule
import com.andef.myfinance.presentation.fragment.ExpensesFragment
import com.andef.myfinance.presentation.fragment.IncomesFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        ExpenseDaoModule::class,
        ExpenseRepositoryModule::class,
        IncomeDaoModule::class,
        IncomeRepositoryModule::class,
        ViewModelModule::class
    ]
)
interface MyFinanceComponent {
    fun inject(expensesFragment: ExpensesFragment)
    fun inject(incomesFragment: IncomesFragment)

    @Component.Factory
    interface MyFinanceFactory {
        fun create(@BindsInstance application: Application): MyFinanceComponent
    }
}