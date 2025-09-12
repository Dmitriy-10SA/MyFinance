package com.andef.myfinance.core.platform.common

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AndroidPermissionManager(private val context: Context) : PermissionManager {
    private val _remindersGranted = MutableStateFlow(true)
    override val remindersGranted: StateFlow<Boolean> = _remindersGranted.asStateFlow()

    override fun refreshRemindersPermissions() {
        _remindersGranted.value =
            hasNotificationPermission(context) && hasUseExactAlarmPermission(context) &&
                    canScheduleExactAlarms(context)
    }

    private fun hasNotificationPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun hasUseExactAlarmPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                "android.permission.USE_EXACT_ALARM"
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun canScheduleExactAlarms(context: Context): Boolean {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }
}