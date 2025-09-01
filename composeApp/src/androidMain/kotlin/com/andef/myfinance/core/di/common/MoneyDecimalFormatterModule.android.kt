package com.andef.myfinance.core.di.common

import com.andef.myfinance.core.platform.common.AndroidMoneyDecimalFormatter
import com.andef.myfinance.core.platform.common.MoneyDecimalFormatter
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun moneyDecimalFormatterModule(): Module = module {
    singleOf(::AndroidMoneyDecimalFormatter).bind<MoneyDecimalFormatter>()
}