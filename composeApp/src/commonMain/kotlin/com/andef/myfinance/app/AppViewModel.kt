package com.andef.myfinance.app

import androidx.lifecycle.ViewModel
import com.andef.myfinance.core.domain.preferences.usecases.GetIsLightThemeAsFlowUseCase
import com.andef.myfinance.core.domain.preferences.usecases.GetIsLightThemeUseCase
import com.andef.myfinance.core.domain.preferences.usecases.GetUsernameAsFlowUseCase
import com.andef.myfinance.core.domain.preferences.usecases.GetUsernameUseCase

class AppViewModel(
    val getIsLightThemeAsFlowUseCase: GetIsLightThemeAsFlowUseCase,
    val getIsLightThemeUseCase: GetIsLightThemeUseCase,
    val getUsernameAsFlowUseCase: GetUsernameAsFlowUseCase,
    val getUsernameUseCase: GetUsernameUseCase,
) : ViewModel()