package com.andef.myfinance.core.platform.common

import kotlinx.coroutines.flow.StateFlow

interface PermissionManager {
    val remindersGranted: StateFlow<Boolean>

    fun refreshRemindersPermissions()
}