package com.andef.myfinance.core.di.backup

import com.andef.myfinance.core.platform.backup.BackupManager
import com.andef.myfinance.core.platform.backup.IOSBackupManager
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun backupModule(): Module = module {
    singleOf(::IOSBackupManager).bind<BackupManager>()
}