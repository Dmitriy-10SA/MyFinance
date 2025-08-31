package com.andef.myfinance.core.di

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.andef.myfinance.db.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun databaseModule(dbName: String): Module = module {
    single {
        AppDatabase(
            AndroidSqliteDriver(schema = AppDatabase.Schema, context = get(), name = dbName)
        )
    }
}