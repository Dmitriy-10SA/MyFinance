package com.andef.myfinance.core.platform.common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNUserNotificationCenter

class IosPermissionManager : PermissionManager {
    private val _remindersGranted = MutableStateFlow(true)
    override val remindersGranted: StateFlow<Boolean> = _remindersGranted.asStateFlow()

    override fun refreshRemindersPermissions() {
        val center = UNUserNotificationCenter.currentNotificationCenter()
        center.requestAuthorizationWithOptions(
            options = UNAuthorizationOptionAlert or UNAuthorizationOptionSound or UNAuthorizationOptionBadge,
            completionHandler = { granted, error ->
                _remindersGranted.value = try {
                    granted
                } catch (_: Exception) {
                    false
                }
            }
        )
    }
}