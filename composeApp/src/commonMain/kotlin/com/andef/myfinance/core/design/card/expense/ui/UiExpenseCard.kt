package com.andef.myfinance.core.design.card.expense.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.andef.myfinance.core.utils.formatters.numbers.formatPriceRuble
import com.andef.myfinance.core.utils.generatters.generateColorFromString
import com.andef.myfinance.core.utils.getters.getImageForExpense
import com.andef.myfinance.core.utils.getters.getTitleForExpense
import com.andef.myfinance.core.utils.grayColor
import com.andef.myfinance.core.domain.expense.entities.Expense

@Composable
fun UiExpenseCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isLightTheme: Boolean,
    expense: Expense
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
            val image = getImageForExpense(expense.category.title)
            if (image != null) {
                Image(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape),
                    painter = image,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Фото для категории расхода"
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .background(generateColorFromString(expense.category.title), CircleShape)
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            TitleAndNote(expense = expense, isLightTheme = isLightTheme)
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = "-${formatPriceRuble(expense.amount)}",
                fontSize = 16.sp,
                color = blackOrWhiteColor(isLightTheme = isLightTheme)
            )
        }
    }
}

@Composable
private fun RowScope.TitleAndNote(expense: Expense, isLightTheme: Boolean) {
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = getTitleForExpense(expense.category.title),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            color = blackOrWhiteColor(isLightTheme = isLightTheme)
        )
        Text(
            text = buildString {
                val note = expense.note
                if (note.isNullOrBlank()) {
                    append("Примечания нет")
                } else {
                    append(note)
                }
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
            color = grayColor(isLightTheme = isLightTheme)
        )
    }
}