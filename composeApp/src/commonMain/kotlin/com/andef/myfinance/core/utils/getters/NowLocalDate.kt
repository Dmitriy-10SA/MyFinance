package com.andef.myfinance.core.utils.getters

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

expect fun LocalDate.Companion.now(): LocalDate

fun LocalDate.minusDays(days: Long) = this.plus(
    period = DatePeriod(days = -days.toInt())
)

fun LocalDate.minusMonths(months: Long) = this.plus(
    period = DatePeriod(months = -months.toInt())
)

fun LocalDate.minusYears(years: Long) = this.plus(
    period = DatePeriod(years = -years.toInt())
)