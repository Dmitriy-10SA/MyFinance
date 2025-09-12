package com.andef.myfinance.core.design.chooser.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
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
    trailingIcon: Painter,
    leadingIconContentDescription: String,
    trailingIconContentDescription: String
) {
    OutlinedTextField(
        modifier = modifier,
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
        trailingIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    painter = trailingIcon,
                    contentDescription = trailingIconContentDescription
                )
            }
        },
        singleLine = true,
        readOnly = true,
        shape = textFieldShape(),
        colors = textFieldColors(value = value, isLightTheme = isLightTheme),
        textStyle = TextStyle(color = blackOrWhiteColor(isLightTheme), fontSize = 16.sp)
    )
}