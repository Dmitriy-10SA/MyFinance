package com.andef.myfinance.feature.expense_common.expense_category.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.andef.myfinance.core.design.alert.dialog.ui.UiAlertDialog
import com.andef.myfinance.core.design.bottom.sheet.ui.UiModalBottomSheet
import com.andef.myfinance.core.design.button.ui.UiButton
import com.andef.myfinance.core.design.card.expense.ui.UiExpenseCategoryCard
import com.andef.myfinance.core.design.fab.ui.UiFAB
import com.andef.myfinance.core.design.loading.ui.UiLoading
import com.andef.myfinance.core.design.scaffold.ui.UiScaffold
import com.andef.myfinance.core.design.snackbar.type.UiSnackbarType
import com.andef.myfinance.core.design.snackbar.ui.UiSnackbar
import com.andef.myfinance.core.design.textfield.ui.UiTextField
import com.andef.myfinance.core.design.topbar.type.UiTopBarType
import com.andef.myfinance.core.design.topbar.ui.UiTopBar
import com.andef.myfinance.core.domain.expense_common.expense_category.entities.BaseExpenseCategory
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.Red
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.getters.getTitleForExpense
import com.andef.myfinance.core.utils.showSnackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_add
import myfinance.composeapp.generated.resources.my_finance_arrow_back
import myfinance.composeapp.generated.resources.my_finance_delete
import myfinance.composeapp.generated.resources.my_finance_edit
import myfinance.composeapp.generated.resources.my_finance_more_horiz
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseCategoryAddScreen(
    isLightTheme: Boolean,
    navHostController: NavHostController
) {
    val viewModel = koinViewModel<ExpenseCategoryAddViewModel>()
    val state = viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val actionsState = rememberModalBottomSheetState()
    val addOrChangeState = rememberModalBottomSheetState()

    LaunchedEffect(Unit) { viewModel.send(ExpenseCategoryAddIntent.SubscribeForExpenseCategories) }

    MainContent(
        isLightTheme = isLightTheme,
        navHostController = navHostController,
        state = state,
        snackbarHostState = snackbarHostState,
        viewModel = viewModel
    )
    UiLoading(isVisible = state.value.isLoading, isLightTheme = isLightTheme)
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
            viewModel.send(ExpenseCategoryAddIntent.SubscribeForExpenseCategories)
        },
        onCancelClick = navHostController::popBackStack
    )
    AddOrChangeBottomSheet(
        sheetState = addOrChangeState,
        isLightTheme = isLightTheme,
        viewModel = viewModel,
        state = state,
        scope = scope,
        snackbarHostState = snackbarHostState
    )
    ActionsBottomSheet(
        sheetState = actionsState,
        isLightTheme = isLightTheme,
        viewModel = viewModel,
        state = state
    )
    DeleteDialog(
        isLightTheme = isLightTheme,
        viewModel = viewModel,
        state = state,
        scope = scope,
        snackbarHostState = snackbarHostState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddOrChangeBottomSheet(
    sheetState: SheetState,
    isLightTheme: Boolean,
    viewModel: ExpenseCategoryAddViewModel,
    state: State<ExpenseCategoryAddState>,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    UiModalBottomSheet(
        isVisible = state.value.addOrChangeExpenseCategoryDialogVisible,
        isLightTheme = isLightTheme,
        onDismissRequest = {
            viewModel.send(ExpenseCategoryAddIntent.AddOrChangeExpenseCategoryDialogVisible(false))
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            UiTextField(
                isLightTheme = isLightTheme,
                value = state.value.currentExpenseCategoryTitle,
                onValueChange = {
                    viewModel.send(ExpenseCategoryAddIntent.ChangeCurrentExpenseCategoryTitle(it))
                },
                modifier = Modifier.fillMaxWidth(),
                placeholderText = "Категория",
                leadingIcon = painterResource(Res.drawable.my_finance_more_horiz),
                contentDescription = "Иконка три горизонтальные точки",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
            UiButton(
                text = "Сохранить",
                onClick = {
                    val id = state.value.currentExpenseCategoryId
                    val oldTitle = state.value.oldTitle
                    val title = state.value.currentExpenseCategoryTitle
                    viewModel.send(
                        ExpenseCategoryAddIntent.AddOrChangeExpenseCategoryDialogVisible(false)
                    )
                    if (id != null) {
                        viewModel.send(
                            ExpenseCategoryAddIntent.ChangeExpenseCategory(
                                id = id,
                                title = title,
                                oldTitle = oldTitle,
                                onError = { msg -> showSnackbar(scope, snackbarHostState, msg) }
                            )
                        )
                    } else {
                        viewModel.send(
                            ExpenseCategoryAddIntent.AddExpenseCategory(
                                title = title,
                                onError = { msg -> showSnackbar(scope, snackbarHostState, msg) }
                            )
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.value.addOrChangeExpenseCategoryButtonEnabled
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActionsBottomSheet(
    sheetState: SheetState,
    isLightTheme: Boolean,
    viewModel: ExpenseCategoryAddViewModel,
    state: State<ExpenseCategoryAddState>
) {
    UiModalBottomSheet(
        isVisible = state.value.actionsDialogVisible,
        isLightTheme = isLightTheme,
        onDismissRequest = {
            viewModel.send(ExpenseCategoryAddIntent.ChangeCurrentExpenseCategoryId(null))
            viewModel.send(ExpenseCategoryAddIntent.ChangeCurrentExpenseCategoryTitle(""))
            viewModel.send(ExpenseCategoryAddIntent.ChangeOldTitle(""))
            viewModel.send(ExpenseCategoryAddIntent.ChangeActionsDialogVisible(false))
        },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = getTitleForExpense(state.value.currentExpenseCategoryTitle),
                fontSize = 16.sp,
                color = blackOrWhiteColor(isLightTheme)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(onClick = {
                        viewModel.send(
                            ExpenseCategoryAddIntent.ChangeActionsDialogVisible(false)
                        )
                        viewModel.send(
                            ExpenseCategoryAddIntent.AddOrChangeExpenseCategoryDialogVisible(true)
                        )
                    })
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.my_finance_edit),
                    tint = blackOrWhiteColor(isLightTheme),
                    contentDescription = "Карандаш (изменить)"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Изменить",
                    color = blackOrWhiteColor(isLightTheme),
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(onClick = {
                        viewModel.send(ExpenseCategoryAddIntent.ChangeDeleteDialogVisible(true))
                    })
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
}

@Composable
private fun DeleteDialog(
    isLightTheme: Boolean,
    viewModel: ExpenseCategoryAddViewModel,
    state: State<ExpenseCategoryAddState>,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    UiAlertDialog(
        isLightTheme = isLightTheme,
        title = "Удаление категории",
        subtitle = " Вы уверены? Все расходы данной категории будут удалены!",
        yesTitle = "Удалить",
        cancelTitle = "Отмена",
        yesTitleColor = Red,
        cancelTitleColor = Blue,
        onDismissRequest = {
            viewModel.send(ExpenseCategoryAddIntent.ChangeDeleteDialogVisible(isVisible = false))
        },
        onYesClick = {
            val id =
                state.value.currentExpenseCategoryId ?: throw IllegalStateException("Id is null")
            val title = state.value.currentExpenseCategoryTitle
            if (title.isEmpty()) throw IllegalStateException("Title is empty")
            viewModel.send(ExpenseCategoryAddIntent.ChangeDeleteDialogVisible(isVisible = false))
            viewModel.send(ExpenseCategoryAddIntent.ChangeActionsDialogVisible(isVisible = false))
            viewModel.send(
                ExpenseCategoryAddIntent.DeleteExpenseCategory(
                    id = id,
                    onError = { msg ->
                        snackbarHostState.currentSnackbarData?.dismiss()
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = msg,
                                withDismissAction = true
                            )
                        }
                    },
                    title = title
                )
            )
        },
        onCancelClick = {
            viewModel.send(ExpenseCategoryAddIntent.ChangeDeleteDialogVisible(isVisible = false))
        },
        isVisible = state.value.showDeleteDialog
    )
}

@Composable
private fun MainContent(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    state: State<ExpenseCategoryAddState>,
    viewModel: ExpenseCategoryAddViewModel,
    snackbarHostState: SnackbarHostState
) {
    UiScaffold(
        isLightTheme = isLightTheme,
        topBar = {
            UiTopBar(
                isLightTheme = isLightTheme,
                type = UiTopBarType.Center,
                title = "Категории расходов",
                navigationIconTint = Blue,
                navigationIcon = painterResource(Res.drawable.my_finance_arrow_back),
                navigationIconContentDescription = "Назад",
                onNavigationIconClick = {
                    if (!state.value.isLoading) navHostController.popBackStack()
                }
            )
        },
        floatingActionButton = {
            UiFAB(
                icon = painterResource(Res.drawable.my_finance_add),
                iconContentDescription = "Добавить категорию",
                onClick = {
                    viewModel.send(ExpenseCategoryAddIntent.ChangeCurrentExpenseCategoryId(null))
                    viewModel.send(ExpenseCategoryAddIntent.ChangeCurrentExpenseCategoryTitle(""))
                    viewModel.send(ExpenseCategoryAddIntent.ChangeOldTitle(""))
                    viewModel.send(
                        ExpenseCategoryAddIntent.AddOrChangeExpenseCategoryDialogVisible(true)
                    )
                }
            )
        }
    ) { topBarPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topBarPadding.calculateTopPadding())
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = BaseExpenseCategory.entries.map { it.title }, key = { it }) {
                UiExpenseCategoryCard(
                    isLightTheme = isLightTheme,
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                    enabled = false,
                    category = it,
                    onClick = {}
                )
            }
            if (state.value.expenseCategories.isNotEmpty()) {
                items(items = state.value.expenseCategories, key = { it.id }) {
                    UiExpenseCategoryCard(
                        isLightTheme = isLightTheme,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem(),
                        category = it.title,
                        onClick = {
                            viewModel.send(
                                ExpenseCategoryAddIntent.ChangeCurrentExpenseCategoryId(
                                    it.id
                                )
                            )
                            viewModel.send(
                                ExpenseCategoryAddIntent.ChangeCurrentExpenseCategoryTitle(
                                    it.title
                                )
                            )
                            viewModel.send(ExpenseCategoryAddIntent.ChangeOldTitle(it.title))
                            viewModel.send(ExpenseCategoryAddIntent.ChangeActionsDialogVisible(true))
                        }
                    )
                }
            }
        }
        UiSnackbar(
            paddingValues = topBarPadding,
            snackbarHostState = snackbarHostState,
            type = UiSnackbarType.Error
        )
    }
}