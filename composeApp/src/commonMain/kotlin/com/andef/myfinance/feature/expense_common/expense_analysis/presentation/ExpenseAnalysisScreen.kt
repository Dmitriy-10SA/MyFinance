package com.andef.myfinance.feature.expense_common.expense_analysis.presentation

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.andef.myfinance.core.utils.getters.minusDays
import com.andef.myfinance.core.utils.getters.minusMonths
import com.andef.myfinance.core.utils.getters.minusYears
import com.andef.myfinance.core.utils.getters.now
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
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
                .navigationBarsPadding(),
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
                        .padding(top = 16.dp, bottom = 12.dp),
                    pieChartData = UiPieChartData(
                        slices = getSlices(totalAmount, expensesForAnalysis)
                    )
                )
            }
            item {
                UiLegendRows(
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
    totalAmount: Double,
    incomeForAnalysis: List<Pair<ExpenseCategoryModel, Double>>
): List<UiLegendAmountItem> {
    return incomeForAnalysis.map {
        UiLegendAmountItem(
            title = getTitleForExpense(it.first.title),
            amount = it.second,
            color = getColorForExpenseCategory(it.first.title),
            percent = (it.second / totalAmount * 100).toFloat(),
            isIncome = false
        )
    }
}

private fun getSlices(
    totalAmount: Double,
    incomeForAnalysis: List<Pair<ExpenseCategoryModel, Double>>
): List<UiPieChartData.Slice> {
    return incomeForAnalysis.map {
        UiPieChartData.Slice(
            value = (it.second / totalAmount * 100).toFloat(),
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
                if (tab.id != selectedTabIndex.value || tab.id == 5) {
                    selectedTabIndex.value = tab.id
                    when (tab.id) {
                        0 -> {
                            lastSelectedTabIndex.value = tab.id
                            startDate.value = LocalDate.now()
                            endDate.value = LocalDate.now()
                        }

                        1 -> {
                            lastSelectedTabIndex.value = tab.id
                            startDate.value = LocalDate.now().minusDays(7)
                            endDate.value = LocalDate.now()
                        }

                        2 -> {
                            lastSelectedTabIndex.value = tab.id
                            startDate.value = LocalDate.now().minusMonths(1)
                            endDate.value = LocalDate.now()
                        }

                        3 -> {
                            lastSelectedTabIndex.value = tab.id
                            startDate.value = LocalDate.now().minusMonths(6)
                            endDate.value = LocalDate.now()
                        }

                        4 -> {
                            lastSelectedTabIndex.value = tab.id
                            startDate.value = LocalDate.now().minusYears(1)
                            endDate.value = LocalDate.now()
                        }

                        else -> {
                            datePickerVisible.value = true
                        }
                    }
                }
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
                            snackbarHostState.showSnackbar(message = msg, withDismissAction = true)
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
    UiTopBarTab(id = 3, title = "Полгода"),
    UiTopBarTab(id = 4, title = "Год"),
    UiTopBarTab(id = 5, title = "Период")
)