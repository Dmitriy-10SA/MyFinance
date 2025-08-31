package com.andef.myfinance.core.di.preferences

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

actual fun settingsModule(): Module = module {
    single {
        NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
    }.bind<Settings>()
}