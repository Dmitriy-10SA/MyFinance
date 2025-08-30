package com.andef.myfinance.core.design.time.picker.ui

import kotlinx.datetime.LocalTime

sealed class MyFinanceSnappedTime(val snappedLocalTime: LocalTime, val snappedIndex: Int) {
    data class Hour(val localTime: LocalTime, val index: Int) :
        MyFinanceSnappedTime(localTime, index)

    data class Minute(val localTime: LocalTime, val index: Int) :
        MyFinanceSnappedTime(localTime, index)

    data class Second(val localTime: LocalTime, val index: Int) :
        MyFinanceSnappedTime(localTime, index)
}