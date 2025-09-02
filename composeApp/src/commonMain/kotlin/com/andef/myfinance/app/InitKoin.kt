package com.andef.myfinance.app

import com.andef.myfinance.core.di.backup.backupModule
import com.andef.myfinance.core.di.common.linkOpenerModule
import com.andef.myfinance.core.di.common.loggerModule
import com.andef.myfinance.core.di.data.databaseModule
import com.andef.myfinance.core.di.data.networkModule
import com.andef.myfinance.core.di.expense_common.expenseCategoryModule
import com.andef.myfinance.core.di.expense_common.expenseModule
import com.andef.myfinance.core.di.income_common.incomeCategoryModule
import com.andef.myfinance.core.di.income_common.incomeModule
import com.andef.myfinance.core.di.preferences.preferencesModule
import com.andef.myfinance.core.di.reminder.reminderModule
import com.andef.myfinance.feature.auth.di.authViewModelModule
import com.andef.myfinance.feature.backup.di.backupViewModelModule
import com.andef.myfinance.feature.expense_common.expense_main.di.expenseMainViewModelModule
import com.andef.myfinance.feature.income_common.income_main.di.incomeMainViewModelModule
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
    val viewModelModules = authViewModelModule + backupViewModelModule +
            incomeMainViewModelModule + expenseMainViewModelModule
    startKoin {
        config?.invoke(this)
        modules(
            modules = listOf(
                databaseModule(),
                networkModule(),
                backupModule(),
                linkOpenerModule(),
                loggerModule(),
                appViewModelModule
            ) + expenseCommonModules + incomeCommonModules + reminderModule +
                    preferencesModule + viewModelModules
        )
    }
}