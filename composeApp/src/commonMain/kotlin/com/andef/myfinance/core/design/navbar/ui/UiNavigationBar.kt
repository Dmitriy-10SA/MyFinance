package com.andef.myfinance.core.design.navbar.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.design.navbar.item.UiNavigationBarItem
import com.andef.myfinance.core.utils.anims.fadeInAnim
import com.andef.myfinance.core.utils.anims.fadeOutAnim
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.darkGrayOrWhiteColor
import com.andef.myfinance.core.utils.navBarColors

@Composable
fun UiNavigationBar(
    isLightTheme: Boolean,
    itemSelected: (UiNavigationBarItem) -> Boolean,
    onItemClick: (UiNavigationBarItem) -> Unit,
    items: List<UiNavigationBarItem>,
    isVisible: Boolean = true
) {
    AnimatedVisibility(visible = isVisible, enter = fadeInAnim(), exit = fadeOutAnim()) {
        Column {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = blackOrWhiteColor(isLightTheme = isLightTheme).copy(alpha = 0.2f)
            )
            NavigationBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = darkGrayOrWhiteColor(isLightTheme = isLightTheme),
                contentColor = blackOrWhiteColor(isLightTheme = isLightTheme)
            ) {
                items.forEach { item ->
                    NavigationBarItem(
                        selected = itemSelected(item),
                        onClick = { onItemClick(item) },
                        icon = {
                            Icon(
                                painter = item.icon,
                                contentDescription = item.contentDescription
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                fontSize = 12.sp,
                                maxLines = 1,
                                textAlign = TextAlign.Center,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        alwaysShowLabel = true,
                        colors = navBarColors(isLightTheme = isLightTheme)
                    )
                }
            }
        }
    }
}