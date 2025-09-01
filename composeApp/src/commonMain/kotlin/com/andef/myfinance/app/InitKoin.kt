package com.andef.myfinance.app

import com.andef.myfinance.core.di.backup.backupModule
import com.andef.myfinance.core.di.databaseModule
import com.andef.myfinance.core.di.expense_common.expenseCategoryModule
import com.andef.myfinance.core.di.expense_common.expenseModule
import com.andef.myfinance.core.di.income_common.incomeCategoryModule
import com.andef.myfinance.core.di.income_common.incomeModule
import com.andef.myfinance.core.di.networkModule
import com.andef.myfinance.core.di.preferences.preferencesModule
import com.andef.myfinance.core.di.reminder.reminderModule
import com.andef.myfinance.feature.auth.di.authViewModelModule
import com.andef.myfinance.feature.backup.di.backupViewModelModule
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

private val appViewModelModule = module {
    viewModelOf(::AppViewModel)
}

fun initKoin(config: KoinAppDeclaration? = null) {
    val expenseCommonModules = expenseModule + expenseCategoryModule
    val incomeCommonModules = incomeModule + incomeCategoryModule
    val viewModelModules = authViewModelModule + backupViewModelModule
    startKoin {
        config?.invoke(this)
        modules(
            modules = listOf(
                databaseModule(),
                networkModule(),
                backupModule(),
                appViewModelModule
            ) + expenseCommonModules + incomeCommonModules + reminderModule +
                    preferencesModule + viewModelModules
        )
    }
}