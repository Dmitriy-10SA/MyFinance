package com.andef.myfinance.feature.expense_common.expense_analysis.presentation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.andef.myfinance.core.design.alert.dialog.ui.UiAlertDialog
import com.andef.myfinance.core.design.card.date.amount.row.UiDateAndAmountRow
import com.andef.myfinance.core.design.date.picker.ui.UiRangeDatePickerDialog
import com.andef.myfinance.core.design.legend.row.ui.UiLegendAmountItem
import com.andef.myfinance.core.design.legend.row.ui.UiLegendRows
import com.andef.myfinance.core.design.loading.ui.UiLoading
import com.andef.myfinance.core.design.piechart.ui.UiPieChart
import com.andef.myfinance.core.design.piechart.ui.UiPieChartData
import com.andef.myfinance.core.design.scaffold.ui.UiScaffold
import com.andef.myfinance.core.design.topbar.type.UiTopBarTab
import com.andef.myfinance.core.design.topbar.type.UiTopBarType
import com.andef.myfinance.core.design.topbar.ui.UiTopBar
import com.andef.myfinance.core.domain.expense_common.expense_category.entities.BaseExpenseCategory
import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel
import com.andef.myfinance.core.platform.common.getPdfPrinter
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.Red
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.generatters.generateColorFromString
import com.andef.myfinance.core.utils.getters.getTitleForExpense
import com.andef.myfinance.core.utils.getters.now
import com.kizitonwose.calendar.core.minusDays
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_arrow_back
import myfinance.composeapp.generated.resources.my_finance_print
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExpenseAnalysisScreen(
    isLightTheme: Boolean,
    navHostController: NavHostController
) {
    val viewModel = koinViewModel<ExpenseAnalysisViewModel>()
    val state = viewModel.state.collectAsState()

    var totalDrag by remember { mutableStateOf(0f) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val startDate = remember { mutableStateOf(LocalDate.now()) }
    val endDate = remember { mutableStateOf(LocalDate.now()) }
    val datePickerVisible = remember { mutableStateOf(false) }
    val lastSelectedTabIndex = remember { mutableIntStateOf(0) }

    LaunchedEffect(startDate.value, endDate.value) {
        viewModel.send(ExpenseAnalysisIntent.LoadExpenses(startDate.value, endDate.value))
    }

    UiScaffold(
        isLightTheme = isLightTheme,
        topBar = {
            TopBar(
                isLightTheme = isLightTheme,
                selectedTabIndex = selectedTabIndex,
                lastSelectedTabIndex = lastSelectedTabIndex,
                startDate = startDate,
                endDate = endDate,
                datePickerVisible = datePickerVisible,
                navHostController = navHostController,
                scope = scope,
                snackbarHostState = snackbarHostState,
                viewModel = viewModel
            )
        }
    ) { topBarPadding ->
        val totalAmount = state.value.totalAmount
        val expensesForAnalysis = state.value.expensesForAnalysis
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topBarPadding.calculateTopPadding())
                .navigationBarsPadding()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = { totalDrag = 0f },
                        onHorizontalDrag = { _, dragAmount ->
                            totalDrag += dragAmount
                        },
                        onDragEnd = {
                            if (totalDrag > 100) {
                                if (selectedTabIndex.value in 1..4) {
                                    onTabClick(
                                        dateTabs[selectedTabIndex.value - 1],
                                        selectedTabIndex,
                                        startDate,
                                        endDate,
                                        lastSelectedTabIndex,
                                        datePickerVisible
                                    )
                                }
                            } else if (totalDrag < -100) {
                                if (selectedTabIndex.value in 0..3) {
                                    onTabClick(
                                        dateTabs[selectedTabIndex.value + 1],
                                        selectedTabIndex,
                                        startDate,
                                        endDate,
                                        lastSelectedTabIndex,
                                        datePickerVisible
                                    )
                                }
                            }
                        }
                    )
                },
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            stickyHeader {
                UiDateAndAmountRow(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 12.dp),
                    isIncome = false,
                    totalAmount = state.value.totalAmount,
                    isLightTheme = isLightTheme,
                    startDate = startDate.value,
                    endDate = endDate.value
                )
            }
            item { Spacer(modifier = Modifier.height(6.dp)) }
            item {
                UiPieChart(
                    modifier = Modifier
                        .size(300.dp)
                        .padding(top = 16.dp, bottom = 12.dp)
                        .animateItem(tween(800, easing = FastOutSlowInEasing)),
                    pieChartData = UiPieChartData(
                        slices = getSlices(totalAmount, expensesForAnalysis)
                    )
                )
            }
            item {
                UiLegendRows(
                    modifier = Modifier.animateItem(tween(800, easing = FastOutSlowInEasing)),
                    isLightTheme = isLightTheme,
                    items = getUiLegendAmountItems(totalAmount, expensesForAnalysis)
                )
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
        }
    }
    UiRangeDatePickerDialog(
        isVisible = datePickerVisible.value,
        isLightTheme = isLightTheme,
        onDismissRequest = {
            selectedTabIndex.value = lastSelectedTabIndex.value
            datePickerVisible.value = false
        },
        onOkClick = { s, e ->
            startDate.value = s
            endDate.value = e
            selectedTabIndex.value = 4
            lastSelectedTabIndex.value = 4
            datePickerVisible.value = false
        }
    )
    UiLoading(isLightTheme = isLightTheme, isVisible = state.value.isLoading)
    UiAlertDialog(
        isLightTheme = isLightTheme,
        isVisible = state.value.isError,
        title = "Ошибка!",
        subtitle = "Не удалось загрузить данные. Попробуйте повторить",
        onDismissRequest = navHostController::popBackStack,
        yesTitle = "Повторить",
        cancelTitle = "Выйти",
        cancelTitleColor = Red,
        yesTitleColor = Blue,
        onYesClick = {
            viewModel.send(ExpenseAnalysisIntent.LoadExpenses(startDate.value, endDate.value))
        },
        onCancelClick = navHostController::popBackStack
    )
}

private fun getUiLegendAmountItems(
    totalAmount: Long,
    incomeForAnalysis: List<Pair<ExpenseCategoryModel, Long>>
): List<UiLegendAmountItem> {
    return incomeForAnalysis.map {
        UiLegendAmountItem(
            title = getTitleForExpense(it.first.title),
            amount = it.second,
            color = getColorForExpenseCategory(it.first.title),
            percent = if (totalAmount == 0L) {
                0f
            } else {
                (it.second.toDouble() / totalAmount.toDouble() * 100).toFloat()
            },
            isIncome = false
        )
    }
}

private fun getSlices(
    totalAmount: Long,
    incomeForAnalysis: List<Pair<ExpenseCategoryModel, Long>>
): List<UiPieChartData.Slice> {
    return incomeForAnalysis.map {
        UiPieChartData.Slice(
            value = if (totalAmount == 0L) {
                0f
            } else {
                (it.second.toDouble() / totalAmount.toDouble() * 100).toFloat()
            },
            color = getColorForExpenseCategory(it.first.title)
        )
    }
}

private fun getColorForExpenseCategory(category: String): Color {
    return when (category) {
        BaseExpenseCategory.PRODUCTS.title -> Color(0xFF4BCFA9)
        BaseExpenseCategory.CAFE.title -> Color(0xFFFF9F59)
        BaseExpenseCategory.HOME.title -> Color(0xFF4A9FF5)
        BaseExpenseCategory.GIFTS.title -> Color(0xFFFFD166)
        BaseExpenseCategory.STUDY.title -> Color(0xFF8E7CC3)
        BaseExpenseCategory.HEALTH.title -> Color(0xFFEB5757)
        BaseExpenseCategory.TRANSPORT.title -> Color(0xFF828282)
        BaseExpenseCategory.SPORT.title -> Color(0xFF27AE60)
        BaseExpenseCategory.CLOTHES.title -> Color(0xFFBB6BD9)
        BaseExpenseCategory.OTHER.title -> Color(0xFFBDBDBD)
        else -> generateColorFromString(category)
    }
}

@Composable
private fun TopBar(
    isLightTheme: Boolean,
    selectedTabIndex: MutableState<Int>,
    lastSelectedTabIndex: MutableState<Int>,
    startDate: MutableState<LocalDate>,
    endDate: MutableState<LocalDate>,
    datePickerVisible: MutableState<Boolean>,
    viewModel: ExpenseAnalysisViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    navHostController: NavHostController
) {
    UiTopBar(
        isLightTheme = isLightTheme,
        type = UiTopBarType.WithTabs(
            tabs = dateTabs,
            selectedTabIndex = selectedTabIndex.value,
            onTabClick = { tab ->
                onTabClick(
                    tab,
                    selectedTabIndex,
                    startDate,
                    endDate,
                    lastSelectedTabIndex,
                    datePickerVisible
                )
            }
        ),
        title = "Анализ расходов",
        navigationIconTint = Blue,
        navigationIcon = painterResource(Res.drawable.my_finance_arrow_back),
        navigationIconContentDescription = "Назад",
        onNavigationIconClick = { navHostController.popBackStack() },
        actions = {
            ActionsForTopBar(
                isLightTheme = isLightTheme,
                viewModel = viewModel,
                startDate = startDate.value,
                endDate = endDate.value,
                scope = scope,
                snackbarHostState = snackbarHostState
            )
        }
    )
}

private fun onTabClick(
    tab: UiTopBarTab,
    selectedTabIndex: MutableState<Int>,
    startDate: MutableState<LocalDate>,
    endDate: MutableState<LocalDate>,
    lastSelectedTabIndex: MutableState<Int>,
    datePickerVisible: MutableState<Boolean>
) {
    if (tab.id != selectedTabIndex.value || tab.id == 4) {
        selectedTabIndex.value = tab.id

        val now = LocalDate.now()

        when (tab.id) {
            // Сегодня
            0 -> {
                lastSelectedTabIndex.value = tab.id
                startDate.value = now
                endDate.value = now
            }

            // Текущая неделя: понедельник — сегодня
            1 -> {
                lastSelectedTabIndex.value = tab.id
                startDate.value = now.minusDays(now.dayOfWeek.ordinal)
                endDate.value = now
            }

            // Текущий месяц: 1-е число месяца — сегодня
            2 -> {
                lastSelectedTabIndex.value = tab.id
                startDate.value = LocalDate(
                    year = now.year,
                    month = now.month.number,
                    day = 1
                )
                endDate.value = now
            }

            // Текущий год: 1 января — сегодня
            3 -> {
                lastSelectedTabIndex.value = tab.id
                startDate.value = LocalDate(
                    year = now.year,
                    month = 1,
                    day = 1
                )
                endDate.value = now
            }

            // Период
            else -> {
                datePickerVisible.value = true
            }
        }
    }
}

@Composable
private fun RowScope.ActionsForTopBar(
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    isLightTheme: Boolean,
    viewModel: ExpenseAnalysisViewModel,
    startDate: LocalDate,
    endDate: LocalDate
) {
    val pdfPrinter = getPdfPrinter()
    IconButton(
        onClick = {
            viewModel.send(
                ExpenseAnalysisIntent.GetExpensesForPdf(
                    onSuccess = { expenses, maxDate, minDate ->
                        pdfPrinter.printExpensePdf(expenses, maxDate, minDate)
                    },
                    onError = { msg ->
                        scope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            snackbarHostState.showSnackbar(
                                message = msg,
                                withDismissAction = true
                            )
                        }
                    },
                    startDate = startDate,
                    endDate = endDate
                )
            )
        },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Transparent,
            contentColor = blackOrWhiteColor(isLightTheme)
        )
    ) {
        Icon(
            painter = painterResource(Res.drawable.my_finance_print),
            contentDescription = "Печать расходов"
        )
    }
}

private val dateTabs = listOf(
    UiTopBarTab(id = 0, title = "День"),
    UiTopBarTab(id = 1, title = "Неделя"),
    UiTopBarTab(id = 2, title = "Месяц"),
    UiTopBarTab(id = 3, title = "Год"),
    UiTopBarTab(id = 4, title = "Период")
)
