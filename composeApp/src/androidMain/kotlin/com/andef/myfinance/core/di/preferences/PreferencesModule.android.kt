package com.andef.myfinance.core.di.preferences

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.scope.get
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun settingsModule(): Module = module {
    single {
        SharedPreferencesSettings(
            get<Context>().getSharedPreferences(
                "sh_prefs",
                MODE_PRIVATE
            )
        )
    }.bind<Settings>()
}