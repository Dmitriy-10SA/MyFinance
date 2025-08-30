package com.andef.myfinance.core.design.down.button.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andef.myfinance.core.design.button.ui.UiButton
import com.andef.myfinance.core.utils.blackOrWhiteColor

@Composable
fun ColumnScope.DownButton(
    isLightTheme: Boolean,
    enabled: Boolean,
    onSaveClick: () -> Unit,
    text: String = "Сохранить"
) {
    Column {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = blackOrWhiteColor(isLightTheme = isLightTheme).copy(alpha = 0.2f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        UiButton(
            text = text,
            onClick = onSaveClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .imePadding(),
            enabled = enabled
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}