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
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.cardColors
import com.andef.myfinance.core.utils.cardShape
import com.andef.myfinance.core.utils.formatters.datetime.formatLocalDate
import com.andef.myfinance.core.utils.formatters.datetime.formatLocalTime
import com.andef.myfinance.core.utils.grayColor
import com.andef.myfinance.core.domain.reminder.entities.Reminder
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_notification_perm
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun UiReminderCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isLightTheme: Boolean,
    reminder: Reminder
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
            TextAndStatus(reminder = reminder, isLightTheme = isLightTheme)
            Spacer(modifier = Modifier.width(3.dp))
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = formatLocalDate(reminder.date),
                    fontSize = 16.sp,
                    color = blackOrWhiteColor(isLightTheme = isLightTheme)
                )
                Text(
                    text = formatLocalTime(reminder.time),
                    fontSize = 14.sp,
                    color = grayColor(isLightTheme = isLightTheme)
                )
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
private fun RowScope.TextAndStatus(reminder: Reminder, isLightTheme: Boolean) {
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = reminder.text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            color = blackOrWhiteColor(isLightTheme = isLightTheme)
        )
        val nowDateTime =
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        Text(
            text = if (reminder.date > nowDateTime.date) {
                "Ожидается"
            } else if (reminder.date == nowDateTime.date && reminder.time > nowDateTime.time) {
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