package com.andef.myfinance.core.design.drawer.sheet.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.darkGrayOrWhiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiModalDrawerSheet(
    isLightTheme: Boolean,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    ModalDrawerSheet(
        drawerState = drawerState,
        drawerShape = RoundedCornerShape(0.dp),
        drawerContainerColor = darkGrayOrWhiteColor(isLightTheme),
        drawerContentColor = blackOrWhiteColor(isLightTheme)
    ) {
        content()
    }
}

@Composable
fun UiModalDrawerSheetInnerItem(
    isLightTheme: Boolean,
    itemText: String,
    icon: Painter,
    iconContentDescription: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = icon,
            contentDescription = iconContentDescription
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = itemText,
            fontSize = 18.sp,
            color = blackOrWhiteColor(isLightTheme)
        )
    }
}