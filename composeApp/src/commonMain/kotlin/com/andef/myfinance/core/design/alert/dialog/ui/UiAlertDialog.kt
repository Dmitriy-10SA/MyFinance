package com.andef.myfinance.core.design.alert.dialog.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.design.dialog.container.ui.UiDialogContainer
import com.andef.myfinance.core.utils.grayColor
import com.andef.myfinance.core.utils.textButtonColors
import com.andef.myfinance.core.utils.textButtonShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiAlertDialog(
    isLightTheme: Boolean,
    isVisible: Boolean,
    title: String,
    subtitle: String? = null,
    onDismissRequest: () -> Unit,
    yesTitle: String = "Да",
    cancelTitle: String = "Отмена",
    cancelTitleColor: Color = Color.Unspecified,
    yesTitleColor: Color = Color.Unspecified,
    onYesClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    if (isVisible) {
        UiDialogContainer(
            isLightTheme = isLightTheme,
            onDismissRequest = onDismissRequest
        ) {
            Content(
                title = title,
                isLightTheme = isLightTheme,
                yesTitle = yesTitle,
                cancelTitle = cancelTitle,
                onYesClick = onYesClick,
                onCancelClick = onCancelClick,
                subtitle = subtitle,
                cancelTitleColor = cancelTitleColor,
                yesTitleColor = yesTitleColor
            )
        }
    }
}

@Composable
private fun Content(
    title: String,
    isLightTheme: Boolean,
    subtitle: String?,
    yesTitle: String,
    cancelTitleColor: Color,
    yesTitleColor: Color,
    cancelTitle: String,
    onYesClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(top = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleAndSubtitle(title = title, subtitle = subtitle)
        Spacer(modifier = Modifier.height(14.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.5.dp,
            color = grayColor(isLightTheme = isLightTheme)
        )
        ButtonsRow(
            isLightTheme = isLightTheme,
            yesTitle = yesTitle,
            cancelTitleColor = cancelTitleColor,
            yesTitleColor = yesTitleColor,
            cancelTitle = cancelTitle,
            onYesClick = onYesClick,
            onCancelClick = onCancelClick
        )
    }
}

@Composable
private fun TitleAndSubtitle(title: String, subtitle: String?) {
    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        text = title,
        fontSize = 19.sp
    )
    subtitle?.let {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            text = subtitle,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun ButtonsRow(
    isLightTheme: Boolean,
    yesTitle: String,
    cancelTitleColor: Color,
    yesTitleColor: Color,
    cancelTitle: String,
    onYesClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Row(
        modifier = Modifier.height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ActionButton(
            modifier = Modifier
                .height(48.dp)
                .weight(1f),
            onClick = onCancelClick,
            isLightTheme = isLightTheme,
            text = cancelTitle,
            color = cancelTitleColor,
            isLeft = true
        )
        VerticalDivider(
            modifier = Modifier.height(48.dp),
            thickness = 0.5.dp,
            color = grayColor(isLightTheme = isLightTheme)
        )
        ActionButton(
            modifier = Modifier
                .height(48.dp)
                .weight(1f),
            onClick = onYesClick,
            isLightTheme = isLightTheme,
            text = yesTitle,
            color = yesTitleColor,
            isLeft = false
        )
    }
}

@Composable
private fun ActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isLightTheme: Boolean,
    text: String,
    color: Color,
    isLeft: Boolean
) {
    val shape = when (isLeft) {
        true -> textButtonShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomEnd = 0.dp
        )

        false -> textButtonShape(
            bottomStart = 0.dp,
            topStart = 0.dp,
            topEnd = 0.dp
        )
    }
    TextButton(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        colors = textButtonColors(isLightTheme = isLightTheme)
    ) {
        Text(
            fontWeight = if (!isLeft) FontWeight.Bold else null,
            text = text,
            fontSize = 16.sp,
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}