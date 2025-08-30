package com.andef.myfinance.core.design.date.picker.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.design.auto.resize.text.ui.AutoResizeText
import network.chaintech.kmp_date_time_picker.ui.datepicker.ExperimentalSnapperApi
import network.chaintech.kmp_date_time_picker.ui.datepicker.SnapperLayoutInfo
import network.chaintech.kmp_date_time_picker.ui.datepicker.rememberLazyListSnapperLayoutInfo
import network.chaintech.kmp_date_time_picker.ui.datepicker.rememberSnapperFlingBehavior
import kotlin.math.absoluteValue

@Composable
fun MyFinanceWheelTextPicker(
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    height: Dp = 128.dp,
    texts: List<String>,
    rowCount: Int,
    selectedDateTextStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(
        color = LocalContentColor.current,
        fontSize = 20.sp
    ),
    defaultDateTextStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(
        color = Color.Black,
        fontSize = 18.sp
    ),
    contentAlignment: Alignment = Alignment.Center,
    onScrollFinished: (snappedIndex: Int) -> Int? = { null },
    onScrollInProgress: (Boolean) -> Unit,
) {
    MyFinanceWheelPicker(
        modifier = modifier,
        startIndex = startIndex,
        count = texts.size,
        rowCount = rowCount,
        height = height,
        onScrollFinished = onScrollFinished,
        texts = texts,
        defaultDateTextStyle = defaultDateTextStyle,
        selectedDateTextStyle = selectedDateTextStyle,
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
    height: Dp = 128.dp,
    onScrollFinished: (snappedIndex: Int) -> Int? = { null },
    texts: List<String>,
    selectedDateTextStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(
        color = LocalContentColor.current,
        fontSize = 20.sp
    ),
    defaultDateTextStyle: TextStyle = MaterialTheme.typography.titleSmall.copy(
        color = Color.Black,
        fontSize = 18.sp
    ),
    contentAlignment: Alignment = Alignment.Center,
    onScrollInProgress: (Boolean) -> Unit,
) {
    val lazyListState = rememberLazyListState(startIndex)
    val snapperLayoutInfo = rememberLazyListSnapperLayoutInfo(lazyListState = lazyListState)
    val isScrollInProgress = lazyListState.isScrollInProgress

    LaunchedEffect(isScrollInProgress, count) {
        if (!isScrollInProgress) {
            onScrollFinished(
                myFinanceCalculateSnappedItemIndex(snapperLayoutInfo) ?: startIndex
            )?.let {
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

    Box(
        modifier = modifier,
    ) {
        LazyColumn(
            modifier = Modifier
                .height(height)
                .wrapContentWidth()
                .myFinanceFadingEdge(topBottomFade),
            state = lazyListState,
            contentPadding = PaddingValues(vertical = height / rowCount * ((rowCount - 1) / 2)),
            flingBehavior = rememberSnapperFlingBehavior(
                lazyListState = lazyListState
            )
        ) {
            items(count) { index ->
                Box(
                    modifier = Modifier
                        .height(height / rowCount)
                        .fillMaxWidth()
                        .alpha(
                            myFinanceCalculateAnimatedAlpha(
                                lazyListState = lazyListState,
                                snapperLayoutInfo = snapperLayoutInfo,
                                index = index,
                                rowCount = rowCount
                            )
                        ),
                    contentAlignment = contentAlignment
                ) {
                    AutoResizeText(
                        fontWeight = defaultDateTextStyle.fontWeight,
                        text = texts[index],
                        maxFontSize = defaultDateTextStyle.fontSize,
                        color = if (myFinanceCalculateSnappedItemIndex(snapperLayoutInfo) == index) selectedDateTextStyle.color else defaultDateTextStyle.color
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalSnapperApi::class)
private fun myFinanceCalculateSnappedItemIndex(snapperLayoutInfo: SnapperLayoutInfo): Int? {
    var currentItemIndex = snapperLayoutInfo.currentItem?.index

    if (snapperLayoutInfo.currentItem?.offset != 0) {
        if (currentItemIndex != null) {
            currentItemIndex++
        }
    }
    return currentItemIndex
}


private fun Modifier.myFinanceFadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }


@OptIn(ExperimentalSnapperApi::class)
@Composable
private fun myFinanceCalculateAnimatedAlpha(
    lazyListState: LazyListState,
    snapperLayoutInfo: SnapperLayoutInfo,
    index: Int,
    rowCount: Int
): Float {

    val distanceToIndexSnap = snapperLayoutInfo.distanceToIndexSnap(index).absoluteValue
    val layoutInfo = remember { derivedStateOf { lazyListState.layoutInfo } }.value
    val viewPortHeight = layoutInfo.viewportSize.height.toFloat()
    val singleViewPortHeight = viewPortHeight / rowCount

    return if (distanceToIndexSnap in 0..singleViewPortHeight.toInt()) {
        1.2f - (distanceToIndexSnap / singleViewPortHeight)
    } else {
        0.2f
    }
}