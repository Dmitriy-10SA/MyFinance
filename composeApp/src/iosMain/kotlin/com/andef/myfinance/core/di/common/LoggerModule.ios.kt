package com.andef.myfinance.core.di.common

import com.andef.myfinance.core.platform.common.IosLogger
import com.andef.myfinance.core.platform.common.Logger
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun loggerModule(): Module = module {
    singleOf(::IosLogger).bind<Logger>()
}