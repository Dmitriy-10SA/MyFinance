package com.andef.myfinance.feature.income_common.income_analysis.presentation

import android.app.Application
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.andef.myfinance.core.design.snackbar.type.UiSnackbarType
import com.andef.myfinance.core.design.snackbar.ui.UiSnackbar
import com.andef.myfinance.core.design.topbar.type.UiTopBarTab
import com.andef.myfinance.core.design.topbar.type.UiTopBarType
import com.andef.myfinance.core.design.topbar.ui.UiTopBar
import com.andef.myfinance.core.domain.income_common.income_category.entities.BaseIncomeCategory
import com.andef.myfinance.core.domain.income_common.income_category.entities.IncomeCategoryModel
import com.andef.myfinance.core.platform.common.InterstitialAdManager
import com.andef.myfinance.core.platform.common.getPdfPrinter
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.Red
import com.andef.myfinance.core.utils.anims.fadeInAnim
import com.andef.myfinance.core.utils.anims.fadeOutAnim
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.generatters.generateColorFromString
import com.andef.myfinance.core.utils.getters.getTitleForIncome
import com.andef.myfinance.core.utils.getters.minusDays
import com.andef.myfinance.core.utils.getters.minusMonths
import com.andef.myfinance.core.utils.getters.minusYears
import com.andef.myfinance.core.utils.getters.now
import com.andef.myfinance.feature.income_common.income_main.presentation.nativeAdAppearance
import com.yandex.mobile.ads.nativeads.template.NativeBannerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_arrow_back
import myfinance.composeapp.generated.resources.my_finance_print
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
actual fun IncomeAnalysisScreen(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    interstitialAdManager: InterstitialAdManager
) {
    val viewModel = koinViewModel<IncomeAnalysisViewModel>()
    val state = viewModel.state.collectAsState()

    val application = LocalContext.current.applicationContext as Application
    val adsViewModel: IncomeAnalysisMainAdsViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return IncomeAnalysisMainAdsViewModel(application) as T
            }
        }
    )
    val adViews = adsViewModel.adViews.collectAsState().value

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val startDate = remember { mutableStateOf(LocalDate.now()) }
    val endDate = remember { mutableStateOf(LocalDate.now()) }
    val datePickerVisible = remember { mutableStateOf(false) }
    val lastSelectedTabIndex = remember { mutableIntStateOf(0) }

    LaunchedEffect(startDate.value, endDate.value) {
        viewModel.send(IncomeAnalysisIntent.LoadIncomes(startDate.value, endDate.value))
    }

    UiScaffold(
        isLightTheme = isLightTheme,
        topBar = {
            TopBar(
                snackbarHostState = snackbarHostState,
                scope = scope,
                isLightTheme = isLightTheme,
                selectedTabIndex = selectedTabIndex,
                lastSelectedTabIndex = lastSelectedTabIndex,
                startDate = startDate,
                endDate = endDate,
                datePickerVisible = datePickerVisible,
                navHostController = navHostController,
                viewModel = viewModel,
                interstitialAdManager = interstitialAdManager
            )
        },
        snackbarHost = {
            UiSnackbar(
                paddingValues = paddingValues,
                snackbarHostState = snackbarHostState,
                type = UiSnackbarType.Error
            )
        }
    ) { topBarPadding ->
        val totalAmount = state.value.totalAmount
        val incomesForAnalysis = state.value.incomesForAnalysis
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topBarPadding.calculateTopPadding())
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            stickyHeader {
                AnimatedVisibility(
                    visible = incomesForAnalysis.isNotEmpty(),
                    exit = fadeOutAnim(),
                    enter = fadeInAnim()
                ) {
                    UiDateAndAmountRow(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 12.dp),
                        totalAmount = state.value.totalAmount,
                        isIncome = true,
                        isLightTheme = isLightTheme,
                        startDate = startDate.value,
                        endDate = endDate.value
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(6.dp)) }
            when (incomesForAnalysis.isEmpty()) {
                true -> {
                    item {
                        Text(
                            text = "Пока нет данных о доходах. Вот несколько предложений для Вас:",
                            color = blackOrWhiteColor(isLightTheme),
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(top = 16.dp)
                                .padding(bottom = 16.dp)
                                .animateItem(tween(810, easing = FastOutSlowInEasing)),
                            textAlign = TextAlign.Center
                        )
                    }
                    items(adViews.size, key = { "$isLightTheme-$it" }) { index ->
                        AndroidView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 14.dp)
                                .animateItem(tween(810, easing = FastOutSlowInEasing)),
                            factory = { context ->
                                NativeBannerView(context).apply {
                                    applyAppearance(nativeAdAppearance(isLightTheme))
                                    setAd(adViews[index])
                                }
                            }
                        )
                    }
                }

                false -> {
                    item {
                        UiPieChart(
                            modifier = Modifier
                                .size(300.dp)
                                .padding(top = 16.dp, bottom = 12.dp),
                            pieChartData = UiPieChartData(
                                slices = getSlices(totalAmount, incomesForAnalysis)
                            )
                        )
                    }
                    item {
                        UiLegendRows(
                            isLightTheme = isLightTheme,
                            items = getUiLegendAmountItems(totalAmount, incomesForAnalysis)
                        )
                    }
                }
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
            viewModel.send(IncomeAnalysisIntent.LoadIncomes(startDate.value, endDate.value))
        },
        onCancelClick = navHostController::popBackStack
    )
}

private fun getUiLegendAmountItems(
    totalAmount: Double,
    incomeForAnalysis: List<Pair<IncomeCategoryModel, Double>>
): List<UiLegendAmountItem> {
    return incomeForAnalysis.map {
        UiLegendAmountItem(
            title = getTitleForIncome(it.first.title),
            amount = it.second,
            isIncome = true,
            color = getColorForIncomeCategory(it.first.title),
            percent = (it.second / totalAmount * 100).toFloat()
        )
    }
}

private fun getSlices(
    totalAmount: Double,
    incomeForAnalysis: List<Pair<IncomeCategoryModel, Double>>
): List<UiPieChartData.Slice> {
    return incomeForAnalysis.map {
        UiPieChartData.Slice(
            value = (it.second / totalAmount * 100).toFloat(),
            color = getColorForIncomeCategory(it.first.title)
        )
    }
}

private fun getColorForIncomeCategory(category: String): Color {
    return when (category) {
        BaseIncomeCategory.SALARY.title -> Color(0xFF4BCFA9)
        BaseIncomeCategory.BANK.title -> Color(0xFF4A9FF5)
        BaseIncomeCategory.LUCK.title -> Color(0xFFFF6B6B)
        BaseIncomeCategory.GIFTS.title -> Color(0xFFFFD166)
        BaseIncomeCategory.OTHER.title -> Color(0xFFA38BFE)
        else -> generateColorFromString(category)
    }
}

@Composable
private fun TopBar(
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    isLightTheme: Boolean,
    selectedTabIndex: MutableState<Int>,
    lastSelectedTabIndex: MutableState<Int>,
    startDate: MutableState<LocalDate>,
    endDate: MutableState<LocalDate>,
    datePickerVisible: MutableState<Boolean>,
    viewModel: IncomeAnalysisViewModel,
    navHostController: NavHostController,
    interstitialAdManager: InterstitialAdManager
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
        title = "Анализ доходов",
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
                snackbarHostState = snackbarHostState,
                interstitialAdManager = interstitialAdManager
            )
        }
    )
}

@Composable
private fun RowScope.ActionsForTopBar(
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    isLightTheme: Boolean,
    viewModel: IncomeAnalysisViewModel,
    startDate: LocalDate,
    endDate: LocalDate,
    interstitialAdManager: InterstitialAdManager
) {
    val pdfPrinter = getPdfPrinter()
    IconButton(
        onClick = {
            interstitialAdManager.showAd {
                viewModel.send(
                    IncomeAnalysisIntent.GetIncomesForPdf(
                        onSuccess = { incomes, maxDate, minDate ->
                            pdfPrinter.printIncomePdf(incomes, maxDate, minDate)
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
            }
        },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.Transparent,
            contentColor = blackOrWhiteColor(isLightTheme)
        )
    ) {
        Icon(
            painter = painterResource(Res.drawable.my_finance_print),
            contentDescription = "Печать доходов"
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