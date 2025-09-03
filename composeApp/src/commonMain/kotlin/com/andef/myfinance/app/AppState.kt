package com.andef.myfinance.app

import com.kizitonwose.calendar.core.now
import kotlinx.datetime.LocalDate
import kotlin.time.ExperimentalTime

data class AppState @OptIn(ExperimentalTime::class) constructor(
    val username: String,
    val isFirstStart: Boolean,
    val selectedTabIndex: Int = 0,
    val lastSelectedTabIndex: Int = 0,
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now(),
    val datePickerVisible: Boolean = false,
    val currentRoute: String? = null,
    val previousRoute: String? = null
)
