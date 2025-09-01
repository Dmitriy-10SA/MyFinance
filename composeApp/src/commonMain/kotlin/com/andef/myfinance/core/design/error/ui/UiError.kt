package com.andef.myfinance.core.design.error.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.design.Black
import com.andef.myfinance.core.design.DarkGray
import com.andef.myfinance.core.design.White
import com.andef.myfinance.core.design.button.ui.UiButton

@Composable
fun UiError(
    isVisible: Boolean,
    paddingValues: PaddingValues,
    isLightTheme: Boolean,
    onRetry: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = scaleIn(tween(400, easing = FastOutSlowInEasing)),
        exit = scaleOut(tween(400, easing = FastOutSlowInEasing))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.padding(horizontal = 48.dp),
                shape = shape,
                border = BorderStroke(width = 2.dp, color = if (isLightTheme) Black else White),
                colors = colors(isLightTheme = isLightTheme)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 18.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Упс, ошибка!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    UiButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Повторить",
                        onClick = onRetry
                    )
                }
            }
        }
    }
}

private val shape = RoundedCornerShape(36.dp)

@Composable
private fun colors(isLightTheme: Boolean) = CardDefaults.cardColors(
    containerColor = if (isLightTheme) White else DarkGray,
    contentColor = if (isLightTheme) Black else White
)