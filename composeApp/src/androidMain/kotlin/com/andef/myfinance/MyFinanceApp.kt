package com.andef.myfinance

import android.app.Application
import com.andef.myfinance.app.initKoin
import org.koin.android.ext.koin.androidContext

class MyFinanceApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin { androidContext(this@MyFinanceApp) }
    }
}