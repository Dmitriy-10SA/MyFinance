package com.andef.myfinance.core.design.legend.row.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.formatters.numbers.format
import com.andef.myfinance.core.utils.formatters.numbers.formatPriceRuble
import com.andef.myfinance.core.utils.grayColor

@Composable
fun UiLegendRows(modifier: Modifier = Modifier, isLightTheme: Boolean, items: List<UiLegendAmountItem>) {
    items.forEach { item ->
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(color = item.color, shape = CircleShape)
                )
                Text(
                    text = "${item.title} (${item.percent.format()}%)",
                    fontSize = 16.sp,
                    color = grayColor(isLightTheme = isLightTheme),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))
                val sign = if (item.isIncome && item.amount != 0.0) {
                    "+"
                } else if (!item.isIncome && item.amount != 0.0) {
                    "-"
                } else {
                    ""
                }
                Text(
                    text = "$sign${formatPriceRuble(item.amount)}",
                    color = blackOrWhiteColor(isLightTheme = isLightTheme),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}