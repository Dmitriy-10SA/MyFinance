package com.andef.myfinance.core.utils.anims

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

fun fadeInAnim(duration: Int = 400, easing: Easing = FastOutSlowInEasing) = fadeIn(
    tween(duration, easing = easing)
)

fun fadeOutAnim(duration: Int = 400, easing: Easing = FastOutSlowInEasing) = fadeOut(
    tween(duration, easing = easing)
)

fun slideInRightHorizontalAnim(
    duration: Int = 400,
    easing: Easing = FastOutSlowInEasing
) = slideInHorizontally(
    animationSpec = tween(durationMillis = duration, easing = easing),
    initialOffsetX = { -it }
)

fun slideOutLeftHorizontalAnim(
    duration: Int = 400,
    easing: Easing = FastOutSlowInEasing
) = slideOutHorizontally(
    animationSpec = tween(durationMillis = duration, easing = easing),
    targetOffsetX = { -it }
)

fun slideInLeftHorizontalAnim(
    duration: Int = 400,
    easing: Easing = FastOutSlowInEasing
) = slideInHorizontally(
    animationSpec = tween(durationMillis = duration, easing = easing),
    initialOffsetX = { it }
)

fun slideOutRightHorizontalAnim(
    duration: Int = 400,
    easing: Easing = FastOutSlowInEasing
) = slideOutHorizontally(
    animationSpec = tween(durationMillis = duration, easing = easing),
    targetOffsetX = { it }
)