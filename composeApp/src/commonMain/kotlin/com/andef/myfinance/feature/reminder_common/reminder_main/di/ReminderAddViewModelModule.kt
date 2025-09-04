package com.andef.myfinance.feature.reminder_common.reminder_main.di

import com.andef.myfinance.feature.reminder_common.reminder_main.presentation.ReminderAddViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val reminderAddViewModelModule = module {
    viewModelOf(::ReminderAddViewModel)
}