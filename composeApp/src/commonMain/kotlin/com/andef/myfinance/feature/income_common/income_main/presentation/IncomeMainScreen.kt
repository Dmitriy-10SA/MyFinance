package com.andef.myfinance.feature.income_common.income_main.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.andef.myfinance.core.design.alert.dialog.ui.UiAlertDialog
import com.andef.myfinance.core.design.bottom.sheet.ui.UiModalBottomSheet
import com.andef.myfinance.core.design.card.date.amount.row.UiDateAndAmountRow
import com.andef.myfinance.core.design.card.income.ui.UiIncomeCard
import com.andef.myfinance.core.design.loading.ui.UiLoading
import com.andef.myfinance.core.design.snackbar.type.UiSnackbarType
import com.andef.myfinance.core.design.snackbar.ui.UiSnackbar
import com.andef.myfinance.core.domain.income_common.income.entities.IncomeModel
import com.andef.myfinance.core.domain.income_common.income_category.entities.IncomeCategoryModel
import com.andef.myfinance.core.navigation.routes.Screen
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.Red
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.formatters.datetime.formatLocalDate
import com.andef.myfinance.core.utils.formatters.numbers.formatPriceRuble
import com.andef.myfinance.core.utils.getters.getTitleForIncome
import com.andef.myfinance.core.utils.grayColor
import com.andef.myfinance.feature.income_common.income_main.domain.entities.IncomeForLazyColumn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_delete
import myfinance.composeapp.generated.resources.my_finance_edit
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeMainScreen(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    startDate: LocalDate,
    endDate: LocalDate
) {
    val viewModel = koinViewModel<IncomeMainViewModel>()
    val state = viewModel.state.collectAsState().value

    val sheetState = rememberModalBottomSheetState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = state.initialFirstVisibleItemIndex,
        initialFirstVisibleItemScrollOffset = state.initialFirstVisibleItemScrollOffset
    )

    DisposableEffect(Unit) {
        onDispose {
            viewModel.send(
                IncomeMainIntent.SaveScrollState(
                    initialFirstVisibleItemIndex = listState.firstVisibleItemIndex,
                    initialFirstVisibleItemScrollOffset = listState.firstVisibleItemScrollOffset
                )
            )
        }
    }

    LaunchedEffect(startDate, endDate) {
        viewModel.send(IncomeMainIntent.SubscribeForIncomes(startDate, endDate))
    }

    MainContent(
        paddingValues = paddingValues,
        totalAmount = state.totalAmount,
        incomesForLazyColumn = state.incomesForLazyColumn,
        isLightTheme = isLightTheme,
        isLoading = state.isLoading,
        startDate = startDate,
        endDate = endDate,
        onIncomeCardClick = { income ->
            viewModel.send(
                IncomeMainIntent.BottomSheetVisibleChange(
                    isVisible = true,
                    date = income.date,
                    category = income.category,
                    amount = income.amount,
                    id = income.id
                )
            )
        },
        listState = listState
    )
    BottomSheetWithDeleteDialog(
        navHostController = navHostController,
        viewModel = viewModel,
        state = state,
        isLightTheme = isLightTheme,
        sheetState = sheetState,
        scope = scope,
        snackbarHostState = snackbarHostState
    )
    UiSnackbar(paddingValues, snackbarHostState, UiSnackbarType.Error)
}

@Composable
private fun MainContent(
    paddingValues: PaddingValues,
    totalAmount: Double,
    incomesForLazyColumn: List<IncomeForLazyColumn>,
    isLightTheme: Boolean,
    isLoading: Boolean,
    startDate: LocalDate,
    endDate: LocalDate,
    listState: LazyListState,
    onIncomeCardClick: (IncomeModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        state = listState,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        stickyHeader {
            UiDateAndAmountRow(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp),
                isIncome = true,
                totalAmount = totalAmount,
                isLightTheme = isLightTheme,
                startDate = startDate,
                endDate = endDate
            )
        }
        item { Spacer(modifier = Modifier.height(6.dp)) }
        incomesForLazyColumn.forEach { incomeForLazyColumn ->
            item(key = "date-${incomeForLazyColumn.date}") {
                Spacer(modifier = Modifier.height(18.dp))
                UiDateAndAmountRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 4.dp)
                        .animateItem(),
                    isIncome = true,
                    isLightTheme = isLightTheme,
                    date = incomeForLazyColumn.date,
                    amount = incomeForLazyColumn.totalAmount
                )
            }
            items(items = incomeForLazyColumn.incomeModels, key = { it.id }) { income ->
                UiIncomeCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                    isLightTheme = isLightTheme,
                    incomeModel = income,
                    onClick = { onIncomeCardClick(income) }
                )
            }
        }
        item { Spacer(modifier = Modifier.height(12.dp)) }
    }
    UiLoading(isVisible = isLoading, isLightTheme = isLightTheme)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetWithDeleteDialog(
    navHostController: NavHostController,
    viewModel: IncomeMainViewModel,
    state: IncomeMainState,
    isLightTheme: Boolean,
    sheetState: SheetState,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    state.idInBottomSheet?.let { id ->
        state.amountInBottomSheet?.let { amount ->
            state.dateInBottomSheet?.let { date ->
                state.categoryInBottomSheet?.let { category ->
                    UiModalBottomSheet(
                        isLightTheme = isLightTheme,
                        isVisible = state.showBottomSheet,
                        onDismissRequest = {
                            viewModel.send(IncomeMainIntent.BottomSheetVisibleChange(false))
                        },
                        sheetState = sheetState,
                    ) {
                        BottomSheetContent(
                            isLightTheme = isLightTheme,
                            category = category,
                            date = date,
                            amount = amount,
                            onEditClick = {
                                viewModel.send(IncomeMainIntent.BottomSheetVisibleChange(false))
                                navHostController.navigate(Screen.IncomeScreen.passId(id))
                            },
                            onDeleteClick = {
                                viewModel.send(IncomeMainIntent.ChangeDeleteDialogVisible(true))
                            }
                        )
                    }
                    DeleteDialog(id, isLightTheme, viewModel, state, scope, snackbarHostState)
                }
            }
        }
    }
}

@Composable
private fun DeleteDialog(
    id: Long,
    isLightTheme: Boolean,
    viewModel: IncomeMainViewModel,
    state: IncomeMainState,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    UiAlertDialog(
        isLightTheme = isLightTheme,
        title = "Удаление дохода",
        onDismissRequest = {
            viewModel.send(IncomeMainIntent.ChangeDeleteDialogVisible(isVisible = false))
        },
        onYesClick = {
            viewModel.send(IncomeMainIntent.ChangeDeleteDialogVisible(isVisible = false))
            viewModel.send(IncomeMainIntent.BottomSheetVisibleChange(isVisible = false))
            viewModel.send(
                IncomeMainIntent.DeleteIncome(
                    id = id,
                    onError = { msg ->
                        snackbarHostState.currentSnackbarData?.dismiss()
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = msg,
                                withDismissAction = true
                            )
                        }
                    }
                )
            )
        },
        cancelTitle = "Отмена",
        yesTitle = "Удалить",
        subtitle = "Вы уверены? Это действие необратимо",
        cancelTitleColor = Blue,
        yesTitleColor = Red,
        onCancelClick = {
            viewModel.send(IncomeMainIntent.ChangeDeleteDialogVisible(isVisible = false))
        },
        isVisible = state.deleteDialogVisible
    )
}

@Composable
private fun BottomSheetContent(
    isLightTheme: Boolean,
    category: IncomeCategoryModel,
    date: LocalDate,
    amount: Double,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Column {
            Text(
                text = getTitleForIncome(category.title),
                fontSize = 16.sp,
                color = blackOrWhiteColor(isLightTheme = isLightTheme)
            )
            Text(
                text = "${formatLocalDate(date)}: +${formatPriceRuble(amount)}",
                fontSize = 14.sp,
                color = grayColor(isLightTheme = isLightTheme)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onEditClick)
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.my_finance_edit),
                tint = blackOrWhiteColor(isLightTheme = isLightTheme),
                contentDescription = "Карандаш (изменить)"
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Изменить", color = blackOrWhiteColor(isLightTheme), fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onDeleteClick)
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(Res.drawable.my_finance_delete),
                tint = Red,
                contentDescription = "Корзина"
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Удалить", color = Red, fontSize = 16.sp)
        }
    }
}