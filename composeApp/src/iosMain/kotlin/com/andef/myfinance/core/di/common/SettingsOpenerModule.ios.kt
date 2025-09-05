package com.andef.myfinance.core.di.common

import com.andef.myfinance.core.platform.common.IosSettingsOpener
import com.andef.myfinance.core.platform.common.SettingsOpener
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun settingsOpenerModule(): Module = module {
    singleOf(::IosSettingsOpener).bind<SettingsOpener>()
}