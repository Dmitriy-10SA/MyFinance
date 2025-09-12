package com.andef.myfinance.core.platform.common

class IosLogger : Logger {
    override fun e(tag: String, message: String) {
        println("$tag: $message")
    }

    override fun d(tag: String, message: String) {
        println("$tag: $message")
    }
}