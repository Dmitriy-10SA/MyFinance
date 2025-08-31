package com.andef.myfinance.core.di.preferences

import com.russhwolf.settings.NSUserDefaultsSettings
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

actual fun preferencesModule(): Module = module {
    single { NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults) }
}