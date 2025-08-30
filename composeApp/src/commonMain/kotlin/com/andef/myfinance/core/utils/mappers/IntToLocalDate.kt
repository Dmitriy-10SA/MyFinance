package com.andef.myfinance.core.utils.mappers

import kotlinx.datetime.LocalDate

fun intToLocalDate(value: Int): LocalDate =
    LocalDate(value / 10000, (value % 10000) / 100, value % 100)