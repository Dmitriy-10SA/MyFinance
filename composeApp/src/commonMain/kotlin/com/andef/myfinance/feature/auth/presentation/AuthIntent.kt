package com.andef.myfinance.feature.auth.presentation

sealed class AuthIntent {
    data class UsernameChange(val username: String) : AuthIntent()
    data class NextClick(
        val onSuccess: (String) -> Unit,
        val onError: (String) -> Unit
    ) : AuthIntent()
}