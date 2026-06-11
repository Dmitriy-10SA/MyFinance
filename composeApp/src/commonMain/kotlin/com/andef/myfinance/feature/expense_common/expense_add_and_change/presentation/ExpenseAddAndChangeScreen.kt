package com.andef.myfinance.feature.expense_common.expense_add_and_change.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.andef.myfinance.core.design.chooser.ui.UiChooser
import com.andef.myfinance.core.design.date.picker.ui.UiDatePickerDialog
import com.andef.myfinance.core.design.down.button.ui.DownButton
import com.andef.myfinance.core.design.loading.ui.UiLoading
import com.andef.myfinance.core.design.menu.ui.UiMenu
import com.andef.myfinance.core.design.scaffold.ui.UiScaffold
import com.andef.myfinance.core.design.snackbar.type.UiSnackbarType
import com.andef.myfinance.core.design.snackbar.ui.UiSnackbar
import com.andef.myfinance.core.design.textfield.ui.UiTextField
import com.andef.myfinance.core.design.topbar.type.UiTopBarType
import com.andef.myfinance.core.design.topbar.ui.UiTopBar
import com.andef.myfinance.core.domain.expense_common.expense_category.entities.BaseExpenseCategory
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.formatters.datetime.formatLocalDate
import com.andef.myfinance.core.utils.formatters.numbers.formatAmountForEdit
import com.andef.myfinance.core.utils.formatters.numbers.parseAmountToKopecks
import com.andef.myfinance.core.utils.generatters.generateColorFromString
import com.andef.myfinance.core.utils.grayColor
import com.andef.myfinance.core.utils.normalizeAmountInput
import com.andef.myfinance.core.utils.showSnackbar
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_arrow_back
import myfinance.composeapp.generated.resources.my_finance_cafe
import myfinance.composeapp.generated.resources.my_finance_calendar
import myfinance.composeapp.generated.resources.my_finance_clothes
import myfinance.composeapp.generated.resources.my_finance_comment
import myfinance.composeapp.generated.resources.my_finance_expense_photo
import myfinance.composeapp.generated.resources.my_finance_gifts
import myfinance.composeapp.generated.resources.my_finance_health
import myfinance.composeapp.generated.resources.my_finance_home
import myfinance.composeapp.generated.resources.my_finance_more_horiz
import myfinance.composeapp.generated.resources.my_finance_other
import myfinance.composeapp.generated.resources.my_finance_products
import myfinance.composeapp.generated.resources.my_finance_ruble
import myfinance.composeapp.generated.resources.my_finance_schedule
import myfinance.composeapp.generated.resources.my_finance_sport
import myfinance.composeapp.generated.resources.my_finance_study
import myfinance.composeapp.generated.resources.my_finance_transport
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun ExpenseAddAndChangeScreen(
    expenseId: Long?,
    isLightTheme: Boolean,
    navHostController: NavHostController,
    paddingValues: PaddingValues
) {
    val viewModel = koinViewModel<ExpenseAddAndChangeViewModel>()
    val state = viewModel.state.collectAsState().value

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val keyboard = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        viewModel.send(
            ExpenseAddAndChangeIntent.InitExpense(
                expenseId = expenseId,
                onError = { msg ->
                    showSnackbar(
                        scope = scope,
                        snackbarHostState = snackbarHostState,
                        message = msg,
                        afterShowCallback = navHostController::popBackStack
                    )
                }
            )
        )
    }

    UiScaffold(
        isLightTheme = isLightTheme,
        topBar = {
            UiTopBar(
                isLightTheme = isLightTheme,
                type = UiTopBarType.Center,
                title = "Расходы",
                navigationIconTint = Blue,
                navigationIcon = painterResource(Res.drawable.my_finance_arrow_back),
                navigationIconContentDescription = "Назад",
                onNavigationIconClick = navHostController::popBackStack
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topBarPadding.calculateTopPadding())
                .navigationBarsPadding()
                .imePadding()
        ) {
            MainContent(
                isLightTheme = isLightTheme,
                scrollState = rememberScrollState(),
                state = state,
                viewModel = viewModel
            )
            DownButton(
                isLightTheme = isLightTheme,
                enabled = state.saveButtonEnabled && !state.isLoading,
                onSaveClick = {
                    keyboard?.hide()
                    viewModel.send(
                        ExpenseAddAndChangeIntent.SaveClick(
                            onSuccess = navHostController::popBackStack,
                            onError = { msg -> showSnackbar(scope, snackbarHostState, msg) }
                        )
                    )
                }
            )
        }
        UiLoading(isVisible = state.isLoading, isLightTheme = isLightTheme)
        UiDatePickerDialog(
            isVisible = state.datePickerVisible,
            isLightTheme = isLightTheme,
            onDismissRequest = {
                viewModel.send(
                    ExpenseAddAndChangeIntent.ChangeDatePickerVisible(
                        false
                    )
                )
            },
            onOkClick = { date ->
                viewModel.send(ExpenseAddAndChangeIntent.ChangeDate(date))
                viewModel.send(ExpenseAddAndChangeIntent.ChangeDatePickerVisible(false))
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColumnScope.MainContent(
    isLightTheme: Boolean,
    scrollState: ScrollState,
    state: ExpenseAddAndChangeState,
    viewModel: ExpenseAddAndChangeViewModel
) {
    var localAmount by rememberSaveable { mutableStateOf("") }
    var initializedAmount by rememberSaveable { mutableStateOf<Long?>(null) }
    LaunchedEffect(state.amount) {
        val amount = state.amount
        if (amount != null && localAmount.isBlank() && initializedAmount != amount) {
            localAmount = formatAmountForEdit(amount)
            initializedAmount = amount
        }
    }
    var typeExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 12.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(6.dp))
        Image(
            modifier = Modifier
                .padding(top = 12.dp)
                .size(130.dp)
                .clip(CircleShape)
                .border(
                    shape = CircleShape,
                    width = 1.dp,
                    color = grayColor(isLightTheme).copy(alpha = 0.3f)
                ),
            contentScale = ContentScale.Crop,
            painter = painterResource(Res.drawable.my_finance_expense_photo),
            contentDescription = "Иконка расходы"
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "Обязательные поля:",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = grayColor(isLightTheme),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        UiTextField(
            isLightTheme = isLightTheme,
            value = localAmount,
            onValueChange = { newText ->
                val normalized = normalizeAmountInput(newText)
                localAmount = normalized

                val parsed = parseAmountToKopecks(normalized)
                viewModel.send(ExpenseAddAndChangeIntent.ChangeAmount(parsed))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholderText = "Сумма (₽)",
            leadingIcon = painterResource(Res.drawable.my_finance_ruble),
            contentDescription = "Значок рубля",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        UiMenu(
            items = state.expenseCategories,
            modifier = Modifier.fillMaxWidth(),
            itemToString = { item ->
                when (item.title) {
                    BaseExpenseCategory.PRODUCTS.titleForUser -> BaseExpenseCategory.PRODUCTS.titleForUser
                    BaseExpenseCategory.CAFE.titleForUser -> BaseExpenseCategory.CAFE.titleForUser
                    BaseExpenseCategory.HOME.titleForUser -> BaseExpenseCategory.HOME.titleForUser
                    BaseExpenseCategory.GIFTS.titleForUser -> BaseExpenseCategory.GIFTS.titleForUser
                    BaseExpenseCategory.STUDY.titleForUser -> BaseExpenseCategory.STUDY.titleForUser
                    BaseExpenseCategory.HEALTH.titleForUser -> BaseExpenseCategory.HEALTH.titleForUser
                    BaseExpenseCategory.TRANSPORT.titleForUser -> BaseExpenseCategory.TRANSPORT.titleForUser
                    BaseExpenseCategory.SPORT.titleForUser -> BaseExpenseCategory.SPORT.titleForUser
                    BaseExpenseCategory.CLOTHES.titleForUser -> BaseExpenseCategory.CLOTHES.titleForUser
                    BaseExpenseCategory.OTHER.titleForUser -> BaseExpenseCategory.OTHER.titleForUser
                    else -> item.title
                }
            },
            itemToLeadingIcon = { item ->
                val image = when (item.title) {
                    BaseExpenseCategory.PRODUCTS.titleForUser -> painterResource(Res.drawable.my_finance_products)
                    BaseExpenseCategory.CAFE.titleForUser -> painterResource(Res.drawable.my_finance_cafe)
                    BaseExpenseCategory.HOME.titleForUser -> painterResource(Res.drawable.my_finance_home)
                    BaseExpenseCategory.GIFTS.titleForUser -> painterResource(Res.drawable.my_finance_gifts)
                    BaseExpenseCategory.STUDY.titleForUser -> painterResource(Res.drawable.my_finance_study)
                    BaseExpenseCategory.HEALTH.titleForUser -> painterResource(Res.drawable.my_finance_health)
                    BaseExpenseCategory.TRANSPORT.titleForUser -> painterResource(Res.drawable.my_finance_transport)
                    BaseExpenseCategory.SPORT.titleForUser -> painterResource(Res.drawable.my_finance_sport)
                    BaseExpenseCategory.CLOTHES.titleForUser -> painterResource(Res.drawable.my_finance_clothes)
                    BaseExpenseCategory.OTHER.titleForUser -> painterResource(Res.drawable.my_finance_other)
                    else -> null
                }
                if (image != null) {
                    Image(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape),
                        painter = image,
                        contentScale = ContentScale.Crop,
                        contentDescription = "Фото для категории расхода"
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(generateColorFromString(item.title), CircleShape)
                            .clip(CircleShape)
                    )
                }
            },
            isLightTheme = isLightTheme,
            value = state.category?.title?.let {
                when (it) {
                    BaseExpenseCategory.PRODUCTS.titleForUser -> BaseExpenseCategory.PRODUCTS.titleForUser
                    BaseExpenseCategory.CAFE.titleForUser -> BaseExpenseCategory.CAFE.titleForUser
                    BaseExpenseCategory.HOME.titleForUser -> BaseExpenseCategory.HOME.titleForUser
                    BaseExpenseCategory.GIFTS.titleForUser -> BaseExpenseCategory.GIFTS.titleForUser
                    BaseExpenseCategory.STUDY.titleForUser -> BaseExpenseCategory.STUDY.titleForUser
                    BaseExpenseCategory.HEALTH.titleForUser -> BaseExpenseCategory.HEALTH.titleForUser
                    BaseExpenseCategory.TRANSPORT.titleForUser -> BaseExpenseCategory.TRANSPORT.titleForUser
                    BaseExpenseCategory.SPORT.titleForUser -> BaseExpenseCategory.SPORT.titleForUser
                    BaseExpenseCategory.CLOTHES.titleForUser -> BaseExpenseCategory.CLOTHES.titleForUser
                    BaseExpenseCategory.OTHER.titleForUser -> BaseExpenseCategory.OTHER.titleForUser
                    else -> it
                }
            } ?: "",
            placeholderText = "Категория",
            textFieldLeadingIcon = painterResource(Res.drawable.my_finance_more_horiz),
            textFieldLeadingIconContentDescription = "Три горизонтальные точки",
            onItemClick = { item ->
                typeExpanded = false
                viewModel.send(ExpenseAddAndChangeIntent.ChangeCategory(item))
            },
            onExpandedChange = { typeExpanded = it },
            expanded = typeExpanded,
        )
        Spacer(modifier = Modifier.height(16.dp))
        UiChooser(
            isLightTheme = isLightTheme,
            value = state.date?.let { formatLocalDate(it) } ?: "",
            onClick = { viewModel.send(ExpenseAddAndChangeIntent.ChangeDatePickerVisible(true)) },
            modifier = Modifier.fillMaxWidth(),
            placeholderText = "Дата",
            leadingIcon = painterResource(Res.drawable.my_finance_schedule),
            leadingIconContentDescription = "Значок часов",
            trailingIcon = painterResource(Res.drawable.my_finance_calendar),
            trailingIconContentDescription = "Значок календаря"
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "Необязательные поля:",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = grayColor(isLightTheme),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        UiTextField(
            isLightTheme = isLightTheme,
            value = state.note ?: "",
            onValueChange = { viewModel.send(ExpenseAddAndChangeIntent.ChangeNote(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            placeholderText = "Примечание",
            leadingIcon = painterResource(Res.drawable.my_finance_comment),
            contentDescription = "Значок комментария",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
}
