package com.andef.myfinance.core.di.common

import com.andef.myfinance.core.platform.IOSLinkOpener
import com.andef.myfinance.core.platform.LinkOpener
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun linkOpenerModule(): Module = module {
    singleOf(::IOSLinkOpener).bind<LinkOpener>()
}