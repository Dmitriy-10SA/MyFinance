package com.andef.myfinance.core.design.chooser.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.textFieldColors
import com.andef.myfinance.core.utils.textFieldShape

@Composable
fun UiChooser(
    onClick: () -> Unit,
    isLightTheme: Boolean,
    value: String,
    modifier: Modifier = Modifier,
    placeholderText: String,
    leadingIcon: Painter,
    leadingIconContentDescription: String
) {
    val shape = textFieldShape()

    Box(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = {},
            placeholder = {
                Text(
                    text = placeholderText,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            leadingIcon = {
                Icon(
                    painter = leadingIcon,
                    contentDescription = leadingIconContentDescription
                )
            },
            singleLine = true,
            readOnly = true,
            shape = shape,
            colors = textFieldColors(value = value, isLightTheme = isLightTheme),
            textStyle = TextStyle(color = blackOrWhiteColor(isLightTheme), fontSize = 16.sp)
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(shape)
                .clickable(role = Role.Button, onClick = onClick)
        )
    }
}
