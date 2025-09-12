package com.andef.myfinance.core.design.time.picker.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.design.dialog.container.ui.UiDialogContainer
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.White
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.darkGrayOrWhiteColor
import com.andef.myfinance.core.utils.grayColor
import com.andef.myfinance.core.utils.textButtonColors
import com.andef.myfinance.core.utils.textButtonShape
import kotlinx.datetime.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiTimePickerDialog(
    isVisible: Boolean,
    isLightTheme: Boolean,
    onDismissRequest: () -> Unit,
    onOkClick: (LocalTime) -> Unit
) {
    if (isVisible) {
        val timePickerState = rememberTimePickerState(is24Hour = true)
        UiDialogContainer(isLightTheme, onDismissRequest) {
            Column {
                TimePicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 28.dp),
                    state = timePickerState,
                    colors = timePickerColors(isLightTheme)
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
                        onClick = {
                            onOkClick(
                                LocalTime(
                                    timePickerState.hour, timePickerState.minute
                                )
                            )
                        },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun timePickerColors(isLightTheme: Boolean) = TimePickerDefaults.colors(
    clockDialColor = darkGrayOrWhiteColor(isLightTheme),
    selectorColor = Blue,
    periodSelectorBorderColor = blackOrWhiteColor(isLightTheme),
    containerColor = darkGrayOrWhiteColor(isLightTheme),
    periodSelectorSelectedContainerColor = Blue,
    periodSelectorUnselectedContainerColor = Color.Transparent,
    periodSelectorSelectedContentColor = White,
    periodSelectorUnselectedContentColor = blackOrWhiteColor(isLightTheme),
    timeSelectorSelectedContainerColor = Blue,
    clockDialSelectedContentColor = White,
    clockDialUnselectedContentColor = blackOrWhiteColor(isLightTheme),
    timeSelectorUnselectedContainerColor = Color.Transparent,
    timeSelectorSelectedContentColor = White,
    timeSelectorUnselectedContentColor = blackOrWhiteColor(isLightTheme)
)