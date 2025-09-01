package com.andef.myfinance.core.di.backup

import com.andef.myfinance.core.platform.AndroidBackupManager
import com.andef.myfinance.core.platform.BackupManager
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun backupModule(): Module = module {
    singleOf(::AndroidBackupManager).bind<BackupManager>()
}