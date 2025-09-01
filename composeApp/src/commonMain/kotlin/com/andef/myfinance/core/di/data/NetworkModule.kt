package com.andef.myfinance.core.di.data

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module

expect fun networkModule(config: HttpClientConfig<*>.() -> Unit = { installBaseConfig() }): Module

fun HttpClientConfig<*>.installBaseConfig() {
    expectSuccess = false
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                isLenient = true
                encodeDefaults = true
            }
        )
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 30_000
        connectTimeoutMillis = 15_000
        socketTimeoutMillis = 30_000
    }
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println("Ktor => $message")
            }
        }
        level = LogLevel.ALL
    }
    install(DefaultRequest) {
        url {
            protocol = URLProtocol.HTTPS
            host = BASE_URL
        }
        header(HttpHeaders.Accept, ContentType.Application.Json)
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }
}

private const val BASE_URL = "https://cdn.jsdelivr.net/npm/@fawazahmed0/"