package com.andef.myfinance.feature.auth.presentation

data class AuthState(
    val username: String = "",
    val nextButtonEnabled: Boolean = false
)
