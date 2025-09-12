package com.andef.myfinance.core.design

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.andef.myfinance.core.utils.Black
import com.andef.myfinance.core.utils.DarkGray
import com.andef.myfinance.core.utils.White

private val DarkColorScheme = darkColorScheme(
    primary = DarkGray,
    secondary = DarkGray,
    tertiary = DarkGray,
    background = DarkGray,
    surface = DarkGray,
    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
    onBackground = White,
    onSurface = White
)

private val LightColorScheme = lightColorScheme(
    primary = White,
    secondary = White,
    tertiary = White,
    background = White,
    surface = White,
    onPrimary = Black,
    onSecondary = Black,
    onTertiary = Black,
    onBackground = Black,
    onSurface = Black
)

@Composable
fun MyFinanceTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}