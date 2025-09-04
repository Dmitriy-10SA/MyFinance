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
import com.andef.myfinance.core.utils.getters.minusDays
import com.andef.myfinance.core.utils.getters.minusMonths
import com.andef.myfinance.core.utils.getters.minusYears
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
            is AppIntent.DatesChoose -> datesChoose(
                startDate = intent.startDate,
                endDate = intent.endDate
            )

            is AppIntent.DatesDismiss -> datesDismiss()

            is AppIntent.TabClick -> tabClick(tab = intent.tab)

            is AppIntent.CurrentRouteChange -> currentRouteChange(route = intent.route)
        }
    }

    private fun currentRouteChange(route: String?) {
        val previousCurrentRoute = _state.value.currentRoute
        _state.value = _state.value.copy(
            previousRoute = previousCurrentRoute,
            currentRoute = route
        )
    }

    private fun tabClick(tab: UiTopBarTab) {
        val selectedTabIndex = _state.value.selectedTabIndex
        if (tab.id != selectedTabIndex || tab.id == 5) {
            val now = LocalDate.now()
            val newLastTabIndexAndDates: Pair<Int, Pair<LocalDate, LocalDate>>? = when (tab.id) {
                0 -> tab.id to (now to now)
                1 -> tab.id to (now.minusDays(7) to now)
                2 -> tab.id to (now.minusMonths(1) to now)
                3 -> tab.id to (now.minusMonths(6) to now)
                4 -> tab.id to (now.minusYears(1) to now)
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
            lastSelectedTabIndex = 5,
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