package com.andef.myfinance.core.design.card.reminder.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.domain.reminder.entities.ReminderModel
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.cardColors
import com.andef.myfinance.core.utils.cardShape
import com.andef.myfinance.core.utils.formatters.datetime.formatLocalDate
import com.andef.myfinance.core.utils.formatters.datetime.formatLocalTime
import com.andef.myfinance.core.utils.getters.now
import com.andef.myfinance.core.utils.grayColor
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_notification_perm
import org.jetbrains.compose.resources.painterResource

@Composable
fun UiReminderCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isLightTheme: Boolean,
    reminderModel: ReminderModel
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        shape = cardShape(),
        colors = cardColors(isLightTheme = isLightTheme)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape),
                painter = painterResource(Res.drawable.my_finance_notification_perm),
                contentScale = ContentScale.Crop,
                contentDescription = "Фото для напоминаний"
            )
            Spacer(modifier = Modifier.width(16.dp))
            TextAndStatus(reminderModel = reminderModel, isLightTheme = isLightTheme)
            Spacer(modifier = Modifier.width(3.dp))
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = formatLocalDate(reminderModel.date),
                    fontSize = 16.sp,
                    color = blackOrWhiteColor(isLightTheme = isLightTheme)
                )
                Text(
                    text = formatLocalTime(reminderModel.time),
                    fontSize = 14.sp,
                    color = grayColor(isLightTheme = isLightTheme)
                )
            }
        }
    }
}

@Composable
private fun RowScope.TextAndStatus(reminderModel: ReminderModel, isLightTheme: Boolean) {
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = reminderModel.text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            color = blackOrWhiteColor(isLightTheme = isLightTheme)
        )
        val nowDate = LocalDate.now()
        val nowTime = LocalTime.now()
        Text(
            text = if (reminderModel.date > nowDate) {
                "Ожидается"
            } else if (reminderModel.date == nowDate && reminderModel.time > nowTime) {
                "Ожидается"
            } else {
                "Завершено"
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
            color = grayColor(isLightTheme = isLightTheme)
        )
    }
}