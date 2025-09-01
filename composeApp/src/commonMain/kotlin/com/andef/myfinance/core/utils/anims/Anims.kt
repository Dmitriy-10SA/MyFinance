package com.andef.myfinance.core.utils.anims

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

fun fadeInAnim(duration: Int = 400, easing: Easing = FastOutSlowInEasing) = fadeIn(
    tween(duration, easing = easing)
)

fun fadeOutAnim(duration: Int = 400, easing: Easing = FastOutSlowInEasing) = fadeOut(
    tween(duration, easing = easing)
)