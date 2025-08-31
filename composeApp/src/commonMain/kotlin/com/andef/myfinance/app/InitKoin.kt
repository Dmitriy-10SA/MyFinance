package com.andef.myfinance.app

import com.andef.myfinance.core.di.databaseModule
import com.andef.myfinance.core.di.expense_common.expenseCommonModule
import com.andef.myfinance.core.di.income_common.incomeCommonModule
import com.andef.myfinance.core.di.networkModule
import com.andef.myfinance.core.di.preferences.preferencesModule
import com.andef.myfinance.core.di.reminder.reminderModule
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val appViewModelModule = module {
    viewModelOf(::AppViewModel)
}

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            modules = listOf(
                databaseModule(),
                networkModule(),
                preferencesModule()
            ) + expenseCommonModule + incomeCommonModule + reminderModule + appViewModelModule
        )
    }
}