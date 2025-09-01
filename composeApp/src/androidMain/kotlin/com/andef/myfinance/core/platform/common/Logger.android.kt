package com.andef.myfinance.core.platform.common

import android.util.Log

class AndroidLogger : Logger {
    override fun e(tag: String, message: String) {
        Log.e(tag, message)
    }

    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }
}