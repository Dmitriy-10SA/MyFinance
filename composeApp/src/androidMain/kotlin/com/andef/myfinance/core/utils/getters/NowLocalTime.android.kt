package com.andef.myfinance.core.utils.getters

import kotlinx.datetime.LocalTime
import kotlinx.datetime.toKotlinLocalTime

actual fun LocalTime.Companion.now(): LocalTime = java.time.LocalTime.now().toKotlinLocalTime()