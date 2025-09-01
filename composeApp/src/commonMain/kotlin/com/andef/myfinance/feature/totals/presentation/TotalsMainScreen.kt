package com.andef.myfinance.feature.totals.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.andef.myfinance.core.design.card.date.amount.row.UiDateAndAmountRow
import com.andef.myfinance.core.design.legend.row.ui.UiLegendAmountItem
import com.andef.myfinance.core.design.legend.row.ui.UiLegendRows
import com.andef.myfinance.core.design.loading.ui.UiLoading
import com.andef.myfinance.core.design.piechart.ui.UiPieChart
import com.andef.myfinance.core.design.piechart.ui.UiPieChartData
import kotlinx.datetime.LocalDate
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TotalMainScreen(
    isLightTheme: Boolean,
    paddingValues: PaddingValues,
    startDate: LocalDate,
    endDate: LocalDate
) {
    val viewModel = koinViewModel<TotalsMainViewModel>()
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(startDate, endDate) {
        viewModel.send(TotalsMainIntent.SubscribeForAllIncomesAndExpenses(startDate, endDate))
    }

    MainContent(paddingValues, state, isLightTheme, startDate, endDate)
}

@Composable
private fun MainContent(
    paddingValues: PaddingValues,
    state: TotalsMainState,
    isLightTheme: Boolean,
    startDate: LocalDate,
    endDate: LocalDate
) {
    val totalAmount = state.totalIncomesAmount + state.totalExpensesAmount
    val incomesAmount = state.totalIncomesAmount
    val expensesAmount = state.totalExpensesAmount
    val incomesPercent = if (totalAmount == 0.0) {
        0f
    } else {
        (incomesAmount / totalAmount * 100).toFloat()
    }
    val expensesPercent = if (totalAmount == 0.0) {
        0f
    } else {
        (expensesAmount / totalAmount * 100).toFloat()
    }
    val slices = mutableListOf(
        UiPieChartData.Slice(
            value = incomesPercent,
            color = Color(0xFF4BCFA9)
        ),
        UiPieChartData.Slice(
            value = expensesPercent,
            color = Color(0xFFFF6B6B)
        )
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val result = incomesAmount - expensesAmount
        stickyHeader {
            UiDateAndAmountRow(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp),
                isIncome = result >= 0,
                totalAmount = result,
                isLightTheme = isLightTheme,
                startDate = startDate,
                endDate = endDate
            )
        }
        item { Spacer(modifier = Modifier.height(6.dp)) }
        item {
            UiPieChart(
                modifier = Modifier
                    .size(300.dp)
                    .padding(top = 16.dp, bottom = 12.dp),
                pieChartData = UiPieChartData(slices = slices)
            )
        }
        item {
            UiLegendRows(
                isLightTheme = isLightTheme,
                items = listOf(
                    UiLegendAmountItem(
                        Color(0xFF4BCFA9),
                        incomesAmount,
                        "Доходы",
                        incomesPercent,
                        isIncome = true
                    ),
                    UiLegendAmountItem(
                        color = Color(0xFFFF6B6B),
                        amount = expensesAmount,
                        title = "Расходы",
                        percent = expensesPercent,
                        isIncome = false
                    )
                )
            )
        }
        item { Spacer(modifier = Modifier.height(12.dp)) }
    }
    UiLoading(isVisible = state.isLoading, isLightTheme = isLightTheme)
}