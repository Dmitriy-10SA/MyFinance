package com.andef.myfinance.core.utils.mappers.localtime

import kotlinx.datetime.LocalTime

fun localTimeToInt(localTime: LocalTime): Int = localTime.hour * 100 + localTime.minute