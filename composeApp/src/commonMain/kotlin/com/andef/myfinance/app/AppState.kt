package com.andef.myfinance.app

import kotlinx.datetime.LocalDate
import network.chaintech.kmp_date_time_picker.utils.now

data class AppState(
    val isLightTheme: Boolean,
    val username: String,
    val isFirstStart: Boolean,
    val selectedTabIndex: Int = 0,
    val lastSelectedTabIndex: Int = 0,
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now(),
    val datePickerVisible: Boolean = false
)
