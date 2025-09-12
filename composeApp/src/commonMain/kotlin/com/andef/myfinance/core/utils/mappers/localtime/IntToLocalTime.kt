package com.andef.myfinance.core.utils.mappers.localtime

import kotlinx.datetime.LocalTime

fun intToLocalTime(value: Int): LocalTime = LocalTime(value / 100, value % 100)