package com.andef.myfinance.feature.auth.presentation

import androidx.lifecycle.ViewModel
import com.andef.myfinance.core.domain.preferences.usecases.SetIsFirstStartUseCase
import com.andef.myfinance.core.domain.preferences.usecases.SetUsernameUseCase
import com.andef.myfinance.core.navigation.routes.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthViewModel(
    private val setUsernameUseCase: SetUsernameUseCase,
    private val setIsFirstStartUseCase: SetIsFirstStartUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    fun send(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.NextClick -> {
                try {
                    setUsernameUseCase.invoke(_state.value.username)
                    setIsFirstStartUseCase.invoke(false)
                    intent.onSuccess(Screen.MainScreens.IncomeMainScreen.route)
                } catch (_: Exception) {
                    intent.onError("Ошибка! Попробуйте ещё раз!")
                }
            }

            is AuthIntent.UsernameChange -> {
                _state.value = _state.value.copy(
                    username = intent.username,
                    nextButtonEnabled = intent.username.length >= 2
                )
            }
        }
    }
}