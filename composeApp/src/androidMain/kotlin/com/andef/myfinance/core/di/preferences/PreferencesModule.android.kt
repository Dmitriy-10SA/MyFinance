package com.andef.myfinance.core.di.preferences

import android.content.Context.MODE_PRIVATE
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun preferencesModule(): Module = module {
    single {
        SharedPreferencesSettings(
            androidContext().getSharedPreferences(
                "sh_prefs",
                MODE_PRIVATE
            )
        )
    }
}