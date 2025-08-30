package com.andef.myfinance.core.design.card.date.amount.row

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.cardColors
import com.andef.myfinance.core.utils.cardShape
import com.andef.myfinance.core.utils.formatters.datetime.formatLocalDate
import com.andef.myfinance.core.utils.formatters.numbers.formatPriceRuble
import com.andef.myfinance.core.utils.grayColor
import kotlinx.datetime.LocalDate

@Composable
fun UiDateAndAmountRow(
    modifier: Modifier = Modifier,
    isLightTheme: Boolean,
    date: LocalDate,
    amount: Double,
    isIncome: Boolean
) {
    val amountText = if (isIncome) {
        "+${formatPriceRuble(amount)}"
    } else {
        "-${formatPriceRuble(amount)}"
    }
    Card(
        modifier = modifier,
        shape = cardShape(),
        colors = cardColors(isLightTheme = isLightTheme)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                color = blackOrWhiteColor(isLightTheme = isLightTheme),
                text = formatLocalDate(date),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                color = grayColor(isLightTheme = isLightTheme),
                text = amountText,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun UiDateAndAmountRow(
    modifier: Modifier = Modifier,
    isLightTheme: Boolean,
    startDate: LocalDate,
    endDate: LocalDate,
    totalAmount: Double
) {
    val dates = if (startDate == endDate) {
        formatLocalDate(startDate)
    } else {
        "${formatLocalDate(startDate)} - ${formatLocalDate(endDate)}"
    }
    Card(
        modifier = modifier.shadow(
            elevation = if (isLightTheme) 8.dp else 3.dp,
            spotColor = blackOrWhiteColor(isLightTheme = isLightTheme),
            shape = RoundedCornerShape(16.dp),
            clip = true
        ),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 1.dp,
            color = grayColor(isLightTheme = isLightTheme).copy(alpha = 0.3f)
        ),
        colors = cardColors(isLightTheme = isLightTheme)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                color = grayColor(isLightTheme = isLightTheme),
                text = dates,
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.padding(vertical = 1.dp))
            Text(
                color = blackOrWhiteColor(isLightTheme = isLightTheme),
                text = "Итого: ${formatPriceRuble(totalAmount)}",
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }
    }
}