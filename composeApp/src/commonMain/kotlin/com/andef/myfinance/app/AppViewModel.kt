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
import com.andef.myfinance.core.utils.getters.now
import com.kizitonwose.calendar.core.minusDays
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number

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

            is AppIntent.DatesDismiss -> {
                datesDismiss()
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
        if (tab.id != selectedTabIndex || tab.id == 4) {
            val now = LocalDate.now()
            val newLastTabIndexAndDates: Pair<Int, Pair<LocalDate, LocalDate>>? = when (tab.id) {
                // Сегодня
                0 -> tab.id to (now to now)

                // Текущая неделя: понедельник — сегодня
                1 -> {
                    val startOfWeek = now.minusDays(now.dayOfWeek.ordinal)
                    tab.id to (startOfWeek to now)
                }

                // Текущий месяц: 1-е число месяца — сегодня
                2 -> {
                    val startOfMonth = LocalDate(year = now.year, month = now.month.number, day = 1)
                    tab.id to (startOfMonth to now)
                }

                // Текущий год: 1 января — сегодня
                3 -> {
                    val startOfYear = LocalDate(year = now.year, month = 1, day = 1)
                    tab.id to (startOfYear to now)
                }

                else -> null
            }
            if (newLastTabIndexAndDates != null) {
                _state.value = _state.value.copy(
                    selectedTabIndex = tab.id,
                    lastSelectedTabIndex = newLastTabIndexAndDates.first,
                    startDate = newLastTabIndexAndDates.second.first,
                    endDate = newLastTabIndexAndDates.second.second
                )
            } else {
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
            datePickerVisible = false
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