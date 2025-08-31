package com.andef.myfinance.core.di

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun networkModule(config: HttpClientConfig<*>.() -> Unit): Module = module {
    single { HttpClient(Darwin) { config() } }
}