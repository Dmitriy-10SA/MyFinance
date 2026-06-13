package com.andef.myfinance.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.core.design.topbar.type.UiTopBarTab
import com.andef.myfinance.core.domain.preferences.usecases.GetIsFirstStartUseCase
import com.andef.myfinance.core.domain.preferences.usecases.GetIsLightThemeAsFlowUseCase
import com.andef.myfinance.core.domain.preferences.usecases.GetIsLightThemeUseCase
import com.andef.myfinance.core.domain.preferences.usecases.GetUsernameAsFlowUseCase
import com.andef.myfinance.core.domain.preferences.usecases.GetUsernameUseCase
import com.andef.myfinance.core.domain.preferences.usecases.SetIsLightThemeUseCase
import com.andef.myfinance.core.domain.preferences.usecases.SetUsernameUseCase
import com.andef.myfinance.core.utils.date.currentDateRangeForTab
import com.andef.myfinance.core.utils.date.selectedMonthRange
import com.andef.myfinance.core.utils.date.selectedYearRange
import com.andef.myfinance.core.utils.getters.now
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class AppViewModel(
    val getIsLightThemeAsFlowUseCase: GetIsLightThemeAsFlowUseCase,
    val getIsLightThemeUseCase: GetIsLightThemeUseCase,
    val setIsLightThemeUseCase: SetIsLightThemeUseCase,
    val setUsernameUseCase: SetUsernameUseCase,
    private val getUsernameAsFlowUseCase: GetUsernameAsFlowUseCase,
    private val getUsernameUseCase: GetUsernameUseCase,
    private val getIsFirstStartUseCase: GetIsFirstStartUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(
        value = AppState(
            username = getUsernameUseCase.invoke(),
            isFirstStart = getIsFirstStartUseCase.invoke()
        )
    )
    val state: StateFlow<AppState> = _state.asStateFlow()

    fun send(intent: AppIntent) {
        when (intent) {
            is AppIntent.DatesChoose -> {
                datesChoose(
                    startDate = intent.startDate,
                    endDate = intent.endDate
                )
            }

            is AppIntent.MonthChoose -> {
                monthChoose(year = intent.year, month = intent.month)
            }

            is AppIntent.YearChoose -> {
                yearChoose(year = intent.year)
            }

            is AppIntent.DatesDismiss -> {
                datesDismiss()
            }

            is AppIntent.MonthDismiss -> {
                monthDismiss()
            }

            is AppIntent.YearDismiss -> {
                yearDismiss()
            }

            is AppIntent.TabClick -> {
                tabClick(tab = intent.tab)
            }

            is AppIntent.CurrentRouteChange -> {
                currentRouteChange(route = intent.route)
            }

            AppIntent.LeftSwipe -> {
                leftSwipe()
            }

            is AppIntent.RightSwipe -> {
                rightSwipe(openDrawerSheet = intent.openDrawerSheet)
            }
        }
    }

    private fun currentRouteChange(route: String?) {
        val previousCurrentRoute = _state.value.currentRoute
        _state.value = _state.value.copy(
            previousRoute = previousCurrentRoute,
            currentRoute = route
        )
    }

    private fun leftSwipe() {
        val currentTabIndex = _state.value.selectedTabIndex
        if (currentTabIndex in 0..3) {
            tabClick(getUiTopBarTapByIndex(currentTabIndex + 1))
        }
    }

    private fun rightSwipe(openDrawerSheet: () -> Unit) {
        val currentTabIndex = _state.value.selectedTabIndex
        if (currentTabIndex in 1..4) {
            tabClick(getUiTopBarTapByIndex(currentTabIndex - 1))
        } else {
            openDrawerSheet()
        }
    }

    private fun getUiTopBarTapByIndex(index: Int): UiTopBarTab = when (index) {
        0 -> dateTabs[0]
        1 -> dateTabs[1]
        2 -> dateTabs[2]
        3 -> dateTabs[3]
        4 -> dateTabs[4]
        else -> throw Exception()
    }

    private val dateTabs = listOf(
        UiTopBarTab(id = 0, title = "День"),
        UiTopBarTab(id = 1, title = "Неделя"),
        UiTopBarTab(id = 2, title = "Месяц"),
        UiTopBarTab(id = 3, title = "Год"),
        UiTopBarTab(id = 4, title = "Период")
    )

    private fun tabClick(tab: UiTopBarTab) {
        val selectedTabIndex = _state.value.selectedTabIndex
        when {
            tab.id == selectedTabIndex && tab.id == 2 -> {
                _state.value = _state.value.copy(monthPickerVisible = true)
            }

            tab.id == selectedTabIndex && tab.id == 3 -> {
                _state.value = _state.value.copy(yearPickerVisible = true)
            }

            tab.id != selectedTabIndex && tab.id in 0..3 -> {
                val range = currentDateRangeForTab(tab.id, LocalDate.now())
                _state.value = _state.value.copy(
                    selectedTabIndex = tab.id,
                    lastSelectedTabIndex = tab.id,
                    startDate = range.first,
                    endDate = range.second
                )
            }

            tab.id == 4 -> {
                _state.value = _state.value.copy(
                    selectedTabIndex = tab.id,
                    datePickerVisible = true
                )
            }
        }
    }

    private fun datesDismiss() {
        val lastSelectedTabIndex = _state.value.lastSelectedTabIndex
        _state.value = _state.value.copy(
            selectedTabIndex = lastSelectedTabIndex,
            datePickerVisible = false
        )
    }

    private fun datesChoose(startDate: LocalDate, endDate: LocalDate) {
        _state.value = _state.value.copy(
            startDate = startDate,
            endDate = endDate,
            lastSelectedTabIndex = 4,
            selectedTabIndex = 4,
            datePickerVisible = false
        )
    }

    private fun monthDismiss() {
        _state.value = _state.value.copy(monthPickerVisible = false)
    }

    private fun yearDismiss() {
        _state.value = _state.value.copy(yearPickerVisible = false)
    }

    private fun monthChoose(year: Int, month: Int) {
        val range = selectedMonthRange(year, month)
        _state.value = _state.value.copy(
            startDate = range.first,
            endDate = range.second,
            lastSelectedTabIndex = 2,
            monthPickerVisible = false
        )
    }

    private fun yearChoose(year: Int) {
        val range = selectedYearRange(year)
        _state.value = _state.value.copy(
            startDate = range.first,
            endDate = range.second,
            lastSelectedTabIndex = 3,
            yearPickerVisible = false
        )
    }

    private fun subscribeForUsername() {
        viewModelScope.launch {
            getUsernameAsFlowUseCase().collect { username ->
                _state.value = _state.value.copy(username = username)
            }
        }
    }

    init {
        subscribeForUsername()
    }
}