package com.andef.myfinance.core.design.fab.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.White
import com.andef.myfinance.core.utils.fabShape

@Composable
fun UiFAB(
    onClick: () -> Unit,
    icon: Painter,
    iconContentDescription: String,
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = scaleIn(tween(400, easing = FastOutSlowInEasing)),
        exit = scaleOut(tween(400, easing = FastOutSlowInEasing))
    ) {
        FloatingActionButton(
            onClick = onClick,
            modifier = modifier,
            shape = fabShape,
            containerColor = Blue,
            contentColor = White
        ) {
            Icon(
                painter = icon,
                contentDescription = iconContentDescription
            )
        }
    }
}