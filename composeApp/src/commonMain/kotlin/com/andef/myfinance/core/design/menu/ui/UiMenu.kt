package com.andef.myfinance.core.design.menu.ui

import androidx.compose.foundation.border
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType.Companion.PrimaryNotEditable
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.darkGrayOrWhiteColor
import com.andef.myfinance.core.utils.grayColor
import com.andef.myfinance.core.utils.textFieldColors
import com.andef.myfinance.core.utils.textFieldShape
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_arrow_drop_down
import myfinance.composeapp.generated.resources.my_finance_arrow_drop_up
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> UiMenu(
    modifier: Modifier = Modifier,
    items: List<T>,
    itemToString: (T) -> String,
    itemToLeadingIcon: @Composable ((T) -> Unit)? = null,
    isLightTheme: Boolean,
    value: String,
    placeholderText: String,
    textFieldLeadingIcon: Painter,
    textFieldLeadingIconContentDescription: String,
    onItemClick: (T) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = onExpandedChange) {
        MenuTextField(
            modifier = modifier,
            value = value,
            placeholderText = placeholderText,
            textFieldLeadingIcon = textFieldLeadingIcon,
            textFieldLeadingIconContentDescription = textFieldLeadingIconContentDescription,
            isLightTheme = isLightTheme,
            expanded = expanded
        )
        MenuDropdownMenu(
            isLightTheme = isLightTheme,
            expanded = expanded,
            onExpandedChange = onExpandedChange,
            items = items,
            onItemClick = onItemClick,
            itemToString = itemToString,
            itemToLeadingIcon = itemToLeadingIcon
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T> ExposedDropdownMenuBoxScope.MenuDropdownMenu(
    isLightTheme: Boolean,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    items: List<T>,
    onItemClick: (T) -> Unit,
    itemToString: (T) -> String,
    itemToLeadingIcon: @Composable ((T) -> Unit)? = null
) {
    ExposedDropdownMenu(
        modifier = Modifier.border(
            width = 1.dp,
            color = grayColor(isLightTheme = isLightTheme),
            shape = textFieldShape()
        ),
        expanded = expanded,
        onDismissRequest = { onExpandedChange(false) },
        shape = textFieldShape(),
        containerColor = darkGrayOrWhiteColor(isLightTheme = isLightTheme)
    ) {
        items.forEach { item ->
            DropdownMenuItem(
                onClick = { onItemClick(item) },
                text = {
                    Text(text = itemToString(item), fontSize = 16.sp)
                },
                leadingIcon = if (itemToLeadingIcon != null) {
                    {
                        itemToLeadingIcon(item)
                    }
                } else {
                    null
                },
                colors = MenuDefaults.itemColors(
                    textColor = grayColor(isLightTheme = isLightTheme),
                    leadingIconColor = grayColor(isLightTheme = isLightTheme)
                ),
                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExposedDropdownMenuBoxScope.MenuTextField(
    modifier: Modifier,
    value: String,
    placeholderText: String,
    textFieldLeadingIcon: Painter,
    textFieldLeadingIconContentDescription: String,
    isLightTheme: Boolean,
    expanded: Boolean
) {
    OutlinedTextField(
        modifier = modifier.menuAnchor(PrimaryNotEditable),
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
                painter = textFieldLeadingIcon,
                contentDescription = textFieldLeadingIconContentDescription
            )
        },
        trailingIcon = {
            Icon(
                painter = if (expanded) {
                    painterResource(Res.drawable.my_finance_arrow_drop_up)
                } else {
                    painterResource(Res.drawable.my_finance_arrow_drop_down)
                },
                contentDescription = "Открытие закрытие меню"
            )
        },
        singleLine = true,
        readOnly = true,
        shape = textFieldShape(),
        colors = textFieldColors(value = value, isLightTheme = isLightTheme),
        textStyle = TextStyle(color = blackOrWhiteColor(isLightTheme), fontSize = 16.sp)
    )
}