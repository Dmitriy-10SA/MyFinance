package com.andef.myfinance.core.design.loading.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andef.myfinance.core.design.dialog.container.ui.UiDialogContainer
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.blackOrWhiteColor
import kotlinx.coroutines.delay
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_icon
import org.jetbrains.compose.resources.painterResource

@Composable
fun UiLoading(
    isVisible: Boolean,
    onDismissRequest: (() -> Unit)? = null,
    isLightTheme: Boolean
) {
    var visible by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(500)
            visible = isVisible
        } else {
            visible = false
        }
    }

    if (visible) {
        UiDialogContainer(isLightTheme = isLightTheme, onDismissRequest = onDismissRequest) {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 18.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(70.dp),
                    painter = painterResource(Res.drawable.my_finance_icon),
                    contentDescription = "Иконка приложения",
                    tint = blackOrWhiteColor(isLightTheme)
                )
                Spacer(modifier = Modifier.height(12.dp))
                LinearProgressIndicator(
                    modifier = Modifier.width(140.dp),
                    color = Blue,
                    trackColor = blackOrWhiteColor(isLightTheme)
                )
                Spacer(modifier = Modifier.height(1.dp))
            }
        }
    }
}