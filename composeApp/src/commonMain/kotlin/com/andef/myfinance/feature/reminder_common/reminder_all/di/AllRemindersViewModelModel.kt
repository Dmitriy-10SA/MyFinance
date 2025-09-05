package com.andef.myfinance.feature.reminder_common.reminder_all.di

import com.andef.myfinance.feature.reminder_common.reminder_all.presentation.AllRemindersViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val allRemindersViewModelModule = module {
    viewModelOf(::AllRemindersViewModel)
}