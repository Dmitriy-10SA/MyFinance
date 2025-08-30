package com.andef.myfinance.core.utils.mappers

import kotlinx.datetime.LocalDate
import kotlinx.datetime.number

fun localDateToInt(date: LocalDate): Int = date.year * 10000 + date.month.number * 100 + date.day