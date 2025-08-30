package com.andef.myfinance.core.design.time.picker.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import network.chaintech.kmp_date_time_picker.ui.timepicker.ExperimentalSnapperApi
import network.chaintech.kmp_date_time_picker.ui.timepicker.calculateAnimatedAlpha
import network.chaintech.kmp_date_time_picker.ui.timepicker.calculateSnappedItemIndex
import network.chaintech.kmp_date_time_picker.ui.timepicker.fadingEdge
import network.chaintech.kmp_date_time_picker.ui.timepicker.rememberLazyListSnapperLayoutInfo
import network.chaintech.kmp_date_time_picker.ui.timepicker.rememberSnapperFlingBehavior

@Composable
fun MyFinanceWheelTextPicker(
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    height: Dp,
    texts: List<String>,
    rowCount: Int,
    selectedTextStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(
        color = LocalContentColor.current,
        fontSize = 20.sp
    ),
    defaultTextStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(
        color = Color.Black,
        fontSize = 18.sp
    ),
    contentAlignment: Alignment = Alignment.Center,
    onScrollFinished: (snappedIndex: Int) -> Int? = { null },
    onScrollInProgress: (Boolean) -> Unit
) {
    MyFinanceWheelPicker(
        modifier = modifier,
        startIndex = startIndex,
        count = texts.size,
        rowCount = rowCount,
        height = height,
        onScrollFinished = onScrollFinished,
        texts = texts,
        defaultTextStyle = defaultTextStyle,
        selectedTextStyle = selectedTextStyle,
        contentAlignment = contentAlignment,
        onScrollInProgress = onScrollInProgress
    )
}

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun MyFinanceWheelPicker(
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    count: Int,
    rowCount: Int,
    height: Dp,
    onScrollFinished: (snappedIndex: Int) -> Int? = { null },
    texts: List<String>,
    selectedTextStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(
        color = LocalContentColor.current,
        fontSize = 20.sp
    ),
    defaultTextStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(
        color = Color.Black,
        fontSize = 18.sp
    ),
    contentAlignment: Alignment = Alignment.Center,
    onScrollInProgress: (Boolean) -> Unit
) {
    val lazyListState = rememberLazyListState(startIndex)
    val snapperLayoutInfo = rememberLazyListSnapperLayoutInfo(lazyListState = lazyListState)
    val isScrollInProgress = lazyListState.isScrollInProgress

    LaunchedEffect(isScrollInProgress, count) {
        if (!isScrollInProgress) {
            onScrollFinished(calculateSnappedItemIndex(snapperLayoutInfo) ?: startIndex)?.let {
                lazyListState.scrollToItem(it)
            }
        }
    }

    LaunchedEffect(isScrollInProgress) {
        onScrollInProgress(isScrollInProgress)
    }

    val topBottomFade = Brush.verticalGradient(
        0f to Color.Transparent,
        0.3f to Color.Black,
        0.7f to Color.Black,
        1f to Color.Transparent
    )

    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .height(height)
                .fadingEdge(topBottomFade),
            state = lazyListState,
            contentPadding = PaddingValues(vertical = height / rowCount * ((rowCount - 1) / 2)),
            flingBehavior = rememberSnapperFlingBehavior(
                lazyListState = lazyListState
            )
        ) {
            items(count) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height / rowCount)
                        .alpha(
                            calculateAnimatedAlpha(
                                lazyListState = lazyListState,
                                snapperLayoutInfo = snapperLayoutInfo,
                                index = index,
                                rowCount = rowCount
                            )
                        ),
                    contentAlignment = contentAlignment
                ) {
                    Text(
                        text = texts[index],
                        style = defaultTextStyle,
                        color = if (calculateSnappedItemIndex(snapperLayoutInfo) == index) selectedTextStyle.color else defaultTextStyle.color,
                        maxLines = 1,
                        fontSize = if (calculateSnappedItemIndex(snapperLayoutInfo) == index) selectedTextStyle.fontSize else defaultTextStyle.fontSize
                    )
                }
            }
        }
    }
}