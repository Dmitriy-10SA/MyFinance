package com.andef.myfinance.feature.currency.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.andef.myfinance.core.design.alert.dialog.ui.UiAlertDialog
import com.andef.myfinance.core.design.card.currency.ui.UiCurrencyCard
import com.andef.myfinance.core.design.loading.ui.UiLoading
import com.andef.myfinance.core.design.scaffold.ui.UiScaffold
import com.andef.myfinance.core.design.topbar.type.UiTopBarTab
import com.andef.myfinance.core.design.topbar.type.UiTopBarType
import com.andef.myfinance.core.design.topbar.ui.UiTopBar
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.Red
import com.andef.myfinance.core.utils.getters.minusDays
import com.andef.myfinance.core.utils.getters.now
import com.andef.myfinance.feature.currency.domain.entities.CurrencyRub
import com.kizitonwose.calendar.core.minusMonths
import com.kizitonwose.calendar.core.minusYears
import kotlinx.datetime.LocalDate
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_arrow_back
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CurrencysScreen(
    isLightTheme: Boolean,
    navHostController: NavHostController
) {
    val viewModel = koinViewModel<CurrencysViewModel>()
    val state = viewModel.state.collectAsState()

    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val date = remember { mutableStateOf(LocalDate.now()) }

    LaunchedEffect(date.value) { viewModel.send(CurrencysIntent.LoadCurrencys(date.value)) }

    UiScaffold(
        isLightTheme = isLightTheme,
        topBar = {
            TopBar(
                isLightTheme = isLightTheme,
                selectedTabIndex = selectedTabIndex,
                date = date,
                navHostController = navHostController
            )
        }
    ) { topBarPadding ->
        val currencys = state.value.currencys
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topBarPadding.calculateTopPadding())
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = currencys, key = { getCurrencyId(it.first) }) { currency ->
                UiCurrencyCard(
                    isLightTheme = isLightTheme,
                    currencyRub = currency.first,
                    percent = currency.second,
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem()
                )
            }
        }
    }
    UiLoading(isLightTheme = isLightTheme, isVisible = state.value.isLoading)
    UiAlertDialog(
        isLightTheme = isLightTheme,
        isVisible = state.value.isError,
        title = "Упс, ошибка!",
        subtitle = "Проверьте подключение к интернету и повторите попытку!",
        onDismissRequest = navHostController::popBackStack,
        yesTitle = "Повторить",
        cancelTitle = "Выйти",
        cancelTitleColor = Red,
        yesTitleColor = Blue,
        onYesClick = { viewModel.send(CurrencysIntent.LoadCurrencys(date.value)) },
        onCancelClick = navHostController::popBackStack
    )
}

private fun getCurrencyId(currencyRub: CurrencyRub): Int = when (currencyRub) {
    is CurrencyRub.Aud -> currencyRub.id
    is CurrencyRub.Btc -> currencyRub.id
    is CurrencyRub.Cad -> currencyRub.id
    is CurrencyRub.Chf -> currencyRub.id
    is CurrencyRub.Cny -> currencyRub.id
    is CurrencyRub.Eth -> currencyRub.id
    is CurrencyRub.Eur -> currencyRub.id
    is CurrencyRub.Gbp -> currencyRub.id
    is CurrencyRub.Hkd -> currencyRub.id
    is CurrencyRub.Jpy -> currencyRub.id
    is CurrencyRub.Usd -> currencyRub.id
}

@Composable
private fun TopBar(
    isLightTheme: Boolean,
    selectedTabIndex: MutableState<Int>,
    date: MutableState<LocalDate>,
    navHostController: NavHostController
) {
    UiTopBar(
        isLightTheme = isLightTheme,
        type = UiTopBarType.WithTabs(
            tabs = tabs,
            selectedTabIndex = selectedTabIndex.value,
            onTabClick = { tab ->
                if (tab.id != selectedTabIndex.value || tab.id == 4) {
                    selectedTabIndex.value = tab.id
                    when (tab.id) {
                        0 -> {
                            date.value = LocalDate.now()
                        }

                        1 -> {
                            date.value = LocalDate.now().minusDays(7)
                        }

                        2 -> {
                            date.value = LocalDate.now().minusMonths(1)
                        }

                        3 -> {
                            date.value = LocalDate.now().minusMonths(6)
                        }

                        else -> {
                            date.value = LocalDate.now().minusYears(1)
                        }
                    }
                }
            }
        ),
        title = "Курс валют",
        navigationIconTint = Blue,
        navigationIcon = painterResource(Res.drawable.my_finance_arrow_back),
        navigationIconContentDescription = "Назад",
        onNavigationIconClick = { navHostController.popBackStack() }
    )
}

private val tabs = listOf(
    UiTopBarTab(id = 0, title = "День"),
    UiTopBarTab(id = 1, title = "Неделю"),
    UiTopBarTab(id = 2, title = "Месяц"),
    UiTopBarTab(id = 3, title = "Полгода"),
    UiTopBarTab(id = 4, title = "Год")
)