package com.andef.myfinance.core.utils.getters

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate

actual fun LocalDate.Companion.now(): LocalDate = java.time.LocalDate.now().toKotlinLocalDate()