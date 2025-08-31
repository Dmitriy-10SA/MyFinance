package com.andef.myfinance.core.di

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.andef.myfinance.db.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun databaseModule(dbName: String): Module = module {
    single { AppDatabase(NativeSqliteDriver(schema = AppDatabase.Schema, name = dbName)) }
}