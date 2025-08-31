package com.andef.myfinance.app

import com.andef.myfinance.core.design.topbar.type.UiTopBarTab
import kotlinx.datetime.LocalDate

sealed class AppIntent {
    class DatesChoose(val startDate: LocalDate, val endDate: LocalDate) : AppIntent()
    object DatesDismiss : AppIntent()
    class TabClick(val tab: UiTopBarTab) : AppIntent()
}