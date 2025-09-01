package com.andef.myfinance.core.platform.common

interface Logger {
    fun e(tag: String, message: String)
    fun d(tag: String, message: String)
}