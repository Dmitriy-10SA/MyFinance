package com.andef.myfinance.core.di

import com.andef.myfinance.core.platform.AndroidLinkOpener
import com.andef.myfinance.core.platform.LinkOpener
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun linkOpenerModule(): Module = module {
    singleOf(::AndroidLinkOpener).bind<LinkOpener>()
}