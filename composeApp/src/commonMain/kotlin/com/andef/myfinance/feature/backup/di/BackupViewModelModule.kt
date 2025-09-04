package com.andef.myfinance.feature.backup.di

import com.andef.myfinance.feature.backup.presentation.main.BackupMainViewModel
import com.andef.myfinance.feature.backup.presentation.start.BackupStartViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val backupViewModelModule = module {
    viewModelOf(::BackupStartViewModel)
    viewModelOf(::BackupMainViewModel)
}