package com.andef.myfinance.core.di.common

import com.andef.myfinance.core.platform.common.IosPermissionManager
import com.andef.myfinance.core.platform.common.PermissionManager
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun permissionManagerModule(): Module = module {
    singleOf(::IosPermissionManager).bind<PermissionManager>()
}