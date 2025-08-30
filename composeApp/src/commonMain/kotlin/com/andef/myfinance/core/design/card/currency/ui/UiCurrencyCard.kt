package com.andef.myfinance.core.design.card.currency.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.cardColors
import com.andef.myfinance.core.utils.cardShape
import com.andef.myfinance.core.utils.formatters.numbers.format
import com.andef.myfinance.core.utils.formatters.numbers.formatPriceRuble
import com.andef.myfinance.core.utils.grayColor
import com.andef.myfinance.feature.currency.domain.entities.CurrencyRub
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_aud_flag
import myfinance.composeapp.generated.resources.my_finance_btc
import myfinance.composeapp.generated.resources.my_finance_cad_flag
import myfinance.composeapp.generated.resources.my_finance_chf_flag
import myfinance.composeapp.generated.resources.my_finance_cny_flag
import myfinance.composeapp.generated.resources.my_finance_eth
import myfinance.composeapp.generated.resources.my_finance_eur_flag
import myfinance.composeapp.generated.resources.my_finance_gbp_flag
import myfinance.composeapp.generated.resources.my_finance_hkd_flag
import myfinance.composeapp.generated.resources.my_finance_jpy_flag
import myfinance.composeapp.generated.resources.my_finance_usa_flag
import org.jetbrains.compose.resources.painterResource

@Composable
fun UiCurrencyCard(
    modifier: Modifier = Modifier,
    isLightTheme: Boolean,
    currencyRub: CurrencyRub,
    percent: Float
) {
    val formatPercent = "${percent.format()}%"
    val percentText = if (percent > 0) {
        "+$formatPercent"
    } else {
        formatPercent
    }
    Card(
        modifier = modifier,
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
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = grayColor(isLightTheme = isLightTheme).copy(alpha = 0.15f),
                        shape = CircleShape
                    ),
                painter = getImageForCurrency(currencyRub),
                contentScale = ContentScale.Crop,
                contentDescription = "Фото для валюты"
            )
            Spacer(modifier = Modifier.width(16.dp))
            TitleAndShortTitleColumn(currencyRub = currencyRub, isLightTheme = isLightTheme)
            Spacer(modifier = Modifier.width(3.dp))
            PriceAndPercentColumn(
                currencyRub = currencyRub,
                isLightTheme = isLightTheme,
                percent = percent,
                percentText = percentText
            )
        }
    }
}

@Composable
private fun RowScope.TitleAndShortTitleColumn(currencyRub: CurrencyRub, isLightTheme: Boolean) {
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = getTitleForCurrency(currencyRub),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            color = blackOrWhiteColor(isLightTheme = isLightTheme)
        )
        Text(
            text = getShortTitleForCurrency(currencyRub),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
            color = grayColor(isLightTheme = isLightTheme)
        )
    }
}

@Composable
private fun PriceAndPercentColumn(
    currencyRub: CurrencyRub,
    isLightTheme: Boolean,
    percent: Float,
    percentText: String
) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = formatPriceRuble(getAmount(currencyRub)),
            fontSize = 16.sp,
            color = blackOrWhiteColor(isLightTheme = isLightTheme)
        )
        Text(
            text = percentText,
            fontSize = 14.sp,
            color = if (percent > 0) Color(0xFF4BCFA9)
            else if (percent == 0f) grayColor(isLightTheme = isLightTheme)
            else Color(0xFFFF6B6B)
        )
    }
}

private fun getAmount(currencyRub: CurrencyRub): Double = when (currencyRub) {
    is CurrencyRub.Aud -> currencyRub.amount
    is CurrencyRub.Btc -> currencyRub.amount
    is CurrencyRub.Cad -> currencyRub.amount
    is CurrencyRub.Chf -> currencyRub.amount
    is CurrencyRub.Cny -> currencyRub.amount
    is CurrencyRub.Eth -> currencyRub.amount
    is CurrencyRub.Eur -> currencyRub.amount
    is CurrencyRub.Gbp -> currencyRub.amount
    is CurrencyRub.Hkd -> currencyRub.amount
    is CurrencyRub.Jpy -> currencyRub.amount
    is CurrencyRub.Usd -> currencyRub.amount
}

private fun getShortTitleForCurrency(currencyRub: CurrencyRub): String = when (currencyRub) {
    is CurrencyRub.Aud -> "AUD"
    is CurrencyRub.Btc -> "BTC"
    is CurrencyRub.Cad -> "CAD"
    is CurrencyRub.Chf -> "CHF"
    is CurrencyRub.Cny -> "CNY"
    is CurrencyRub.Eth -> "ETH"
    is CurrencyRub.Eur -> "EUR"
    is CurrencyRub.Gbp -> "GBP"
    is CurrencyRub.Hkd -> "HKD"
    is CurrencyRub.Jpy -> "JPY"
    is CurrencyRub.Usd -> "USD"
}

private fun getTitleForCurrency(currencyRub: CurrencyRub): String = when (currencyRub) {
    is CurrencyRub.Aud -> "Австралийский доллар"
    is CurrencyRub.Btc -> "Биткойн"
    is CurrencyRub.Cad -> "Канадский доллар"
    is CurrencyRub.Chf -> "Швейцарский франк"
    is CurrencyRub.Cny -> "Китайский юань"
    is CurrencyRub.Eth -> "Эфириум"
    is CurrencyRub.Eur -> "Евро"
    is CurrencyRub.Gbp -> "Британский фунт"
    is CurrencyRub.Hkd -> "Гонконгский доллар"
    is CurrencyRub.Jpy -> "Японская иена"
    is CurrencyRub.Usd -> "Доллар США"
}

@Composable
private fun getImageForCurrency(currencyRub: CurrencyRub): Painter = when (currencyRub) {
    is CurrencyRub.Aud -> painterResource(Res.drawable.my_finance_aud_flag)
    is CurrencyRub.Btc -> painterResource(Res.drawable.my_finance_btc)
    is CurrencyRub.Cad -> painterResource(Res.drawable.my_finance_cad_flag)
    is CurrencyRub.Chf -> painterResource(Res.drawable.my_finance_chf_flag)
    is CurrencyRub.Cny -> painterResource(Res.drawable.my_finance_cny_flag)
    is CurrencyRub.Eth -> painterResource(Res.drawable.my_finance_eth)
    is CurrencyRub.Eur -> painterResource(Res.drawable.my_finance_eur_flag)
    is CurrencyRub.Gbp -> painterResource(Res.drawable.my_finance_gbp_flag)
    is CurrencyRub.Hkd -> painterResource(Res.drawable.my_finance_hkd_flag)
    is CurrencyRub.Jpy -> painterResource(Res.drawable.my_finance_jpy_flag)
    is CurrencyRub.Usd -> painterResource(Res.drawable.my_finance_usa_flag)
}