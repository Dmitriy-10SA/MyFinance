package com.andef.myfinance.core.di

import org.koin.core.module.Module

expect fun databaseModule(dbName: String = DB_NAME): Module

private const val DB_NAME = "my-car-db"