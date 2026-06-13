package com.andef.myfinance.app

import com.andef.myfinance.core.design.topbar.type.UiTopBarTab
import kotlinx.datetime.LocalDate

sealed class AppIntent {
    class DatesChoose(val startDate: LocalDate, val endDate: LocalDate) : AppIntent()
    class MonthChoose(val year: Int, val month: Int) : AppIntent()
    class YearChoose(val year: Int) : AppIntent()
    object DatesDismiss : AppIntent()
    object MonthDismiss : AppIntent()
    object YearDismiss : AppIntent()
    class TabClick(val tab: UiTopBarTab) : AppIntent()
    class CurrentRouteChange(val route: String?) : AppIntent()
    object LeftSwipe : AppIntent()
    class RightSwipe(val openDrawerSheet: () -> Unit) : AppIntent()
}