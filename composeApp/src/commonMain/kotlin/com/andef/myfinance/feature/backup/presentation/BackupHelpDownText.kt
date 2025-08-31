package com.andef.myfinance.feature.backup.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.design.bottom.sheet.ui.UiConnectionModalBottomSheet
import com.andef.myfinance.core.utils.grayColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackupHelpDownText(
    isLightTheme: Boolean,
    onHelpClick: () -> Unit,
    connectionBottomSheetState: SheetState,
    connectionBottomSheetVisible: Boolean,
    onDismissRequest: () -> Unit,
    onTelegramClick: () -> Unit,
    onMailClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Нужна помощь?",
            color = grayColor(isLightTheme = isLightTheme),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onHelpClick)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
    UiConnectionModalBottomSheet(
        isLightTheme = isLightTheme,
        isVisible = connectionBottomSheetVisible,
        onDismissRequest = onDismissRequest,
        sheetState = connectionBottomSheetState,
        onTelegramClick = onTelegramClick,
        onMailClick = onMailClick,
        text = "Нужна помощь? Напишите разработчику:"
    )
}