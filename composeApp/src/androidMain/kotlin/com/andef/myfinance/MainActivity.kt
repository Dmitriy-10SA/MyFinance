package com.andef.myfinance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.andef.myfinance.app.App
import com.andef.myfinance.core.domain.preferences.usecases.GetIsLightThemeAsFlowUseCase
import com.andef.myfinance.core.domain.preferences.usecases.GetIsLightThemeUseCase
import com.andef.myfinance.core.utils.DarkGray
import com.andef.myfinance.core.utils.White
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.android.ext.android.getKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val isLightTheme = getKoin().get<GetIsLightThemeAsFlowUseCase>()
                .invoke()
                .collectAsState(
                    getKoin().get<GetIsLightThemeUseCase>()
                        .invoke(isSystemInDarkTheme())
                )
            SystemUiSettings(
                systemUiController = rememberSystemUiController(),
                isLightTheme = isLightTheme.value
            )
            App()
        }
    }
}

@Composable
private fun SystemUiSettings(systemUiController: SystemUiController, isLightTheme: Boolean) {
    with(systemUiController) {
        val color = when (isLightTheme) {
            true -> White
            false -> DarkGray
        }
        setNavigationBarColor(color = color, darkIcons = isLightTheme)
        setStatusBarColor(color = color, darkIcons = isLightTheme)
    }
}