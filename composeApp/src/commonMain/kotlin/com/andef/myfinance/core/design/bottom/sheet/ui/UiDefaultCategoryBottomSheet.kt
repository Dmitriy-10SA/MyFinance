package com.andef.myfinance.core.design.bottom.sheet.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.darkGrayOrWhiteColor
import com.andef.myfinance.core.utils.grayColor
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_more_horiz
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> UiDefaultCategoryBottomSheet(
    isLightTheme: Boolean,
    isVisible: Boolean,
    sheetState: SheetState,
    title: String,
    categories: List<T>,
    selectedCategory: T?,
    categoryKey: (T) -> Any,
    categoryTitle: (T) -> String,
    categoryIcon: @Composable (T) -> Unit,
    onDismissRequest: () -> Unit,
    onCategoryClick: (T?) -> Unit
) {
    UiModalBottomSheet(
        isLightTheme = isLightTheme,
        isVisible = isVisible,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            textAlign = TextAlign.Center,
            text = title,
            fontSize = 16.sp,
            color = grayColor(isLightTheme)
        )
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = blackOrWhiteColor(isLightTheme).copy(alpha = 0.2f)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item { Spacer(modifier = Modifier.height(0.dp)) }
            item(key = "no-default-category") {
                DefaultCategoryCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    title = "Не выбирать автоматически",
                    selected = selectedCategory == null,
                    isLightTheme = isLightTheme,
                    onClick = { onCategoryClick(null) }
                )
            }
            items(items = categories, key = categoryKey) { category ->
                DefaultCategoryCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    title = categoryTitle(category),
                    selected = selectedCategory == category,
                    isLightTheme = isLightTheme,
                    leadingIcon = { categoryIcon(category) },
                    onClick = { onCategoryClick(category) }
                )
            }
            item { Spacer(modifier = Modifier.height(0.dp)) }
        }
    }
}

@Composable
private fun DefaultCategoryCard(
    modifier: Modifier,
    title: String,
    selected: Boolean,
    isLightTheme: Boolean,
    onClick: () -> Unit,
    leadingIcon: (@Composable () -> Unit)? = null
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = darkGrayOrWhiteColor(isLightTheme),
            contentColor = blackOrWhiteColor(isLightTheme)
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) Blue else grayColor(isLightTheme).copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            if (leadingIcon != null) {
                leadingIcon()
            } else {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(Res.drawable.my_finance_more_horiz),
                    contentDescription = null,
                    tint = blackOrWhiteColor(isLightTheme)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                fontSize = 16.sp,
                color = blackOrWhiteColor(isLightTheme),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
