package com.andef.myfinance.feature.expense_common.expense_main.presentation

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
import com.andef.myfinance.core.design.card.expense.ui.UiExpenseCard
import com.andef.myfinance.core.design.loading.ui.UiLoading
import com.andef.myfinance.core.design.snackbar.type.UiSnackbarType
import com.andef.myfinance.core.design.snackbar.ui.UiSnackbar
import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel
import com.andef.myfinance.core.navigation.routes.Screen
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.Red
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.formatters.datetime.formatLocalDate
import com.andef.myfinance.core.utils.formatters.numbers.formatPriceRuble
import com.andef.myfinance.core.utils.getters.getTitleForExpense
import com.andef.myfinance.core.utils.grayColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_delete
import myfinance.composeapp.generated.resources.my_finance_edit
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(markerClass = [ExperimentalMaterial3Api::class])
@Composable
actual fun ExpenseMainScreen(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    startDate: LocalDate,
    endDate: LocalDate
) {
    val viewModel = koinViewModel<ExpenseMainViewModel>()
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
                ExpenseMainIntent.SaveScrollState(
                    initialFirstVisibleItemIndex = listState.firstVisibleItemIndex,
                    initialFirstVisibleItemScrollOffset = listState.firstVisibleItemScrollOffset
                )
            )
        }
    }

    LaunchedEffect(startDate, endDate) {
        viewModel.send(ExpenseMainIntent.SubscribeForExpenses(startDate, endDate))
    }

    MainContent(paddingValues, state, isLightTheme, viewModel, startDate, endDate, listState)
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
    state: ExpenseMainState,
    isLightTheme: Boolean,
    viewModel: ExpenseMainViewModel,
    startDate: LocalDate,
    endDate: LocalDate,
    listState: LazyListState
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
                isIncome = false,
                totalAmount = state.totalAmount,
                isLightTheme = isLightTheme,
                startDate = startDate,
                endDate = endDate
            )
        }
        item { Spacer(modifier = Modifier.height(6.dp)) }
        state.expensesForLazyColumn.forEach { expensesForLazyColumn ->
            item(key = "date-${expensesForLazyColumn.date}") {
                Spacer(modifier = Modifier.height(18.dp))
                UiDateAndAmountRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 4.dp)
                        .animateItem(),
                    isIncome = false,
                    isLightTheme = isLightTheme,
                    date = expensesForLazyColumn.date,
                    amount = expensesForLazyColumn.totalAmount
                )
            }
            items(items = expensesForLazyColumn.expenseModels, key = { it.id }) { expense ->
                UiExpenseCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                    isLightTheme = isLightTheme,
                    expenseModel = expense,
                    onClick = {
                        viewModel.send(
                            ExpenseMainIntent.BottomSheetVisibleChange(
                                isVisible = true,
                                date = expense.date,
                                category = expense.category,
                                amount = expense.amount,
                                id = expense.id
                            )
                        )
                    }
                )
            }
        }
        item { Spacer(modifier = Modifier.height(12.dp)) }
    }
    UiLoading(isVisible = state.isLoading, isLightTheme = isLightTheme)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetWithDeleteDialog(
    navHostController: NavHostController,
    viewModel: ExpenseMainViewModel,
    state: ExpenseMainState,
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
                            viewModel.send(ExpenseMainIntent.BottomSheetVisibleChange(false))
                        },
                        sheetState = sheetState,
                    ) {
                        BottomSheetContent(
                            isLightTheme = isLightTheme,
                            category = category,
                            date = date,
                            amount = amount,
                            onEditClick = {
                                viewModel.send(ExpenseMainIntent.BottomSheetVisibleChange(false))
                                navHostController.navigate(Screen.ExpenseScreen.passId(id))
                            },
                            onDeleteClick = {
                                viewModel.send(ExpenseMainIntent.ChangeDeleteDialogVisible(true))
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
    viewModel: ExpenseMainViewModel,
    state: ExpenseMainState,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    UiAlertDialog(
        isLightTheme = isLightTheme,
        title = "Удаление расхода",
        onDismissRequest = {
            viewModel.send(ExpenseMainIntent.ChangeDeleteDialogVisible(isVisible = false))
        },
        onYesClick = {
            viewModel.send(ExpenseMainIntent.ChangeDeleteDialogVisible(isVisible = false))
            viewModel.send(ExpenseMainIntent.BottomSheetVisibleChange(isVisible = false))
            viewModel.send(
                ExpenseMainIntent.DeleteExpense(
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
        onCancelClick = {
            viewModel.send(ExpenseMainIntent.ChangeDeleteDialogVisible(isVisible = false))
        },
        cancelTitle = "Отмена",
        yesTitle = "Удалить",
        subtitle = "Вы уверены? Это действие необратимо!",
        cancelTitleColor = Blue,
        yesTitleColor = Red,
        isVisible = state.deleteDialogVisible
    )
}

@Composable
private fun BottomSheetContent(
    isLightTheme: Boolean,
    category: ExpenseCategoryModel,
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
                text = getTitleForExpense(category.title),
                fontSize = 16.sp,
                color = blackOrWhiteColor(isLightTheme)
            )
            Text(
                text = "${formatLocalDate(date)}: -${formatPriceRuble(amount)}",
                fontSize = 14.sp,
                color = grayColor(isLightTheme)
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
                tint = blackOrWhiteColor(isLightTheme),
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