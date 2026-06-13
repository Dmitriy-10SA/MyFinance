package com.andef.myfinance.core.design.date.picker.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.design.auto.resize.text.ui.AutoResizeText
import com.andef.myfinance.core.design.dialog.container.ui.UiDialogContainer
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.White
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.getters.minusYears
import com.andef.myfinance.core.utils.getters.now
import com.andef.myfinance.core.utils.grayColor
import com.andef.myfinance.core.utils.textButtonColors
import com.andef.myfinance.core.utils.textButtonShape
import com.kizitonwose.calendar.core.plusYears
import kotlinx.datetime.LocalDate

@Composable
fun UiYearPickerDialog(
    isVisible: Boolean,
    isLightTheme: Boolean,
    initialYear: Int,
    onDismissRequest: () -> Unit,
    onOkClick: (year: Int) -> Unit
) {
    if (isVisible) {
        val currentYear = LocalDate.now().year.toString()

        var selectedYear by remember { mutableIntStateOf(initialYear) }
        var firstYear by remember { mutableIntStateOf(initialYear - initialYear.mod(12)) }

        UiDialogContainer(isLightTheme = isLightTheme, onDismissRequest = onDismissRequest) {
            Column(modifier = Modifier.widthIn(max = 360.dp)) {
                Header(
                    isLightTheme = isLightTheme,
                    selectedYear = selectedYear,
                    firstYear = firstYear,
                    onPreviousYearsClick = {
                        firstYear -= 12
                    },
                    onNextYearsClick = { firstYear += 12 }
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 0.5.dp,
                    color = grayColor(isLightTheme = isLightTheme)
                )
                YearsGrid(
                    isLightTheme = isLightTheme,
                    selectedYear = selectedYear,
                    firstYear = firstYear,
                    onYearClick = { selectedYear = it },
                    currentYear = currentYear
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 0.5.dp,
                    color = grayColor(isLightTheme = isLightTheme)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    ActionButton(
                        modifier = Modifier.matchParentSize(),
                        onClick = { onOkClick(selectedYear) },
                        isLightTheme = isLightTheme,
                        text = "Сохранить",
                        color = Blue
                    )
                }
            }
        }
    }
}

@Composable
private fun Header(
    isLightTheme: Boolean,
    selectedYear: Int,
    firstYear: Int,
    onPreviousYearsClick: () -> Unit,
    onNextYearsClick: () -> Unit
) {
    val currentDate = LocalDate.now()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 8.dp)
            .padding(horizontal = 10.dp)
    ) {
        AutoResizeText(
            modifier = Modifier.fillMaxWidth(),
            text = selectedYear.toString(),
            color = blackOrWhiteColor(isLightTheme = isLightTheme),
            maxFontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            YearsButton(
                text = "<",
                isLightTheme = isLightTheme,
                enabled = firstYear >= currentDate.minusYears(12).year,
                onClick = {
                    onPreviousYearsClick()
                }
            )
            Text(
                text = "$firstYear - ${firstYear + 11}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = blackOrWhiteColor(isLightTheme = isLightTheme),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            YearsButton(
                text = ">",
                isLightTheme = isLightTheme,
                enabled = firstYear <= currentDate.plusYears(12).year,
                onClick = {
                    onNextYearsClick()
                }
            )
        }
    }
}

@Composable
private fun YearsGrid(
    isLightTheme: Boolean,
    selectedYear: Int,
    firstYear: Int,
    currentYear: String,
    onYearClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        (firstYear..firstYear + 11).chunked(3).forEach { rowYears ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowYears.forEach { year ->
                    PickerItem(
                        modifier = Modifier.weight(1f),
                        text = year.toString(),
                        selected = year == selectedYear,
                        isLightTheme = isLightTheme,
                        currentYear = currentYear,
                        onClick = { onYearClick(year) }
                    )
                }
            }
        }
    }
}

@Composable
private fun PickerItem(
    modifier: Modifier,
    text: String,
    selected: Boolean,
    isLightTheme: Boolean,
    currentYear: String,
    onClick: () -> Unit
) {
    val backgroundColor = if (selected) Blue else Color.Transparent
    val textColor = when {
        selected -> White
        else -> blackOrWhiteColor(isLightTheme = isLightTheme)
    }
    val textDecoration = when {
        currentYear == text && !selected -> TextDecoration.Underline
        else -> null
    }
    Text(
        modifier = modifier
            .height(42.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 6.dp, vertical = 11.dp),
        text = text,
        textDecoration = textDecoration,
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = textColor
    )
}

@Composable
private fun YearsButton(
    text: String,
    isLightTheme: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    TextButton(
        enabled = enabled,
        onClick = onClick,
        colors = textButtonColors(isLightTheme = isLightTheme)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = if (enabled) Blue else Blue.copy(alpha = 0.3f)
        )
    }
}

@Composable
private fun ActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isLightTheme: Boolean,
    text: String,
    color: Color
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        shape = textButtonShape(topEnd = 0.dp, topStart = 0.dp),
        colors = textButtonColors(isLightTheme = isLightTheme)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
