package com.andef.myfinance.core.utils.getters

import kotlinx.datetime.LocalTime
import kotlinx.datetime.toKotlinLocalTime

actual fun nowLocalTime(): LocalTime {
    return java.time.LocalTime.now().toKotlinLocalTime()
}