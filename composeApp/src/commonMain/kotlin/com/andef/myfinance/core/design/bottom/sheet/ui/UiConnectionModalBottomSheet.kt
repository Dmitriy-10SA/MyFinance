package com.andef.myfinance.core.design.bottom.sheet.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.design.app.item.ui.UiAppItem
import com.andef.myfinance.core.utils.grayColor
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_mail_icon
import myfinance.composeapp.generated.resources.my_finance_telegram_icon
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiConnectionModalBottomSheet(
    isLightTheme: Boolean,
    isVisible: Boolean,
    text: String,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    onTelegramClick: () -> Unit,
    onMailClick: () -> Unit
) {
    UiModalBottomSheet(
        isLightTheme = isLightTheme,
        isVisible = isVisible,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = text,
                color = grayColor(isLightTheme = isLightTheme),
                fontSize = 16.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                textAlign = TextAlign.Center
            )
            AppsRow(
                isLightTheme = isLightTheme,
                onTelegramClick = onTelegramClick,
                onMailClick = onMailClick
            )
        }
    }
}

@Composable
private fun AppsRow(isLightTheme: Boolean, onTelegramClick: () -> Unit, onMailClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        UiAppItem(
            isLightTheme = isLightTheme,
            icon = painterResource(Res.drawable.my_finance_telegram_icon),
            contentDescription = "Иконка телеграмм",
            text = "Telegram",
            onClick = onTelegramClick
        )
        UiAppItem(
            isLightTheme = isLightTheme,
            icon = painterResource(Res.drawable.my_finance_mail_icon),
            contentDescription = "Иконка почты",
            text = "Mail",
            onClick = onMailClick
        )
    }
}