package com.andef.myfinance.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andef.myfinance.core.design.MyFinanceTheme
import com.andef.myfinance.core.design.alert.dialog.ui.UiAlertDialog
import com.andef.myfinance.core.design.bottom.sheet.ui.UiModalBottomSheet
import com.andef.myfinance.core.design.button.ui.UiButton
import com.andef.myfinance.core.design.card.currency.ui.UiCurrencyCard
import com.andef.myfinance.core.design.card.date.amount.row.UiDateAndAmountRow
import com.andef.myfinance.core.design.card.expense.ui.UiExpenseCard
import com.andef.myfinance.core.design.card.expense.ui.UiExpenseCategoryCard
import com.andef.myfinance.core.design.card.income.ui.UiIncomeCard
import com.andef.myfinance.core.design.card.income.ui.UiIncomeCategoryCard
import com.andef.myfinance.core.design.card.reminder.ui.UiReminderCard
import com.andef.myfinance.core.design.chooser.ui.UiChooser
import com.andef.myfinance.core.design.date.picker.ui.UiDatePickerDialog
import com.andef.myfinance.core.design.date.picker.ui.UiRangeDatePickerDialog
import com.andef.myfinance.core.design.legend.row.ui.UiLegendAmountItem
import com.andef.myfinance.core.design.legend.row.ui.UiLegendRows
import com.andef.myfinance.core.design.loading.ui.UiLoading
import com.andef.myfinance.core.design.menu.ui.UiMenu
import com.andef.myfinance.core.design.navbar.item.UiNavigationBarItem
import com.andef.myfinance.core.design.navbar.ui.UiNavigationBar
import com.andef.myfinance.core.design.piechart.ui.UiPieChart
import com.andef.myfinance.core.design.piechart.ui.UiPieChartData
import com.andef.myfinance.core.design.scaffold.ui.UiScaffold
import com.andef.myfinance.core.design.snackbar.type.UiSnackbarType
import com.andef.myfinance.core.design.snackbar.ui.UiSnackbar
import com.andef.myfinance.core.design.textfield.ui.UiTextField
import com.andef.myfinance.core.design.time.picker.ui.UiTimePickerDialog
import com.andef.myfinance.core.design.topbar.type.UiTopBarType
import com.andef.myfinance.core.design.topbar.ui.UiTopBar
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.Red
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.feature.currency.domain.entities.CurrencyRub
import com.andef.myfinance.core.domain.expense.entities.Expense
import com.andef.myfinance.core.domain.expense_category.entities.ExpenseCategory
import com.andef.myfinance.core.domain.income.entities.Income
import com.andef.myfinance.core.domain.income_category.entities.IncomeCategory
import com.andef.myfinance.core.domain.reminder.entities.Reminder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_arrow_drop_up
import myfinance.composeapp.generated.resources.my_finance_expenses
import myfinance.composeapp.generated.resources.my_finance_incomes
import myfinance.composeapp.generated.resources.my_finance_menu
import myfinance.composeapp.generated.resources.my_finance_totals
import network.chaintech.kmp_date_time_picker.utils.now
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    var isLightTheme by remember { mutableStateOf(true) }

    var currentRoute by remember { mutableStateOf("1") }
    var buttonsEnabled by remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    MyFinanceTheme(darkTheme = !isLightTheme) {
        UiScaffold(
            isLightTheme = isLightTheme,
            bottomBar = {
                UiNavigationBar(
                    isLightTheme = isLightTheme,
                    items = bottomBarItems(),
                    itemSelected = { item -> item.route == currentRoute },
                    onItemClick = { item -> currentRoute = item.route }
                )
            },
            topBar = {
                UiTopBar(
                    isLightTheme = isLightTheme,
                    type = UiTopBarType.Center,
                    title = when (currentRoute) {
                        "1" -> "Первый"
                        "2" -> "Второй"
                        "3" -> "Третий"
                        else -> "Четвертая"
                    },
                    navigationIcon = painterResource(Res.drawable.my_finance_menu),
                    onNavigationIconClick = {
                        isLightTheme = !isLightTheme
                    },
                    actions = if (currentRoute == "1") {
                        {
                            IconButton(onClick = { buttonsEnabled = !buttonsEnabled }) {
                                Icon(
                                    painterResource(Res.drawable.my_finance_arrow_drop_up),
                                    null
                                )
                            }
                        }
                    } else {
                        {}
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = if (currentRoute != "3") 16.dp else 0.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (currentRoute) {
                    "1" -> FirstContent(isLightTheme = isLightTheme, enabled = buttonsEnabled)
                    "2" -> SecondContent(isLightTheme = isLightTheme)
                    "3" -> ThirdContent(isLightTheme = isLightTheme)
                    "4" -> FourthContent(isLightTheme = isLightTheme)
                }
            }
            UiSnackbar(
                paddingValues = it,
                snackbarHostState = snackbarHostState,
                type = UiSnackbarType.Error
            )
        }
    }
}

@Composable
private fun FourthContent(isLightTheme: Boolean) {
    UiPieChart(
        pieChartData = UiPieChartData(
            slices = listOf(
                UiPieChartData.Slice(value = 10f, color = Red),
                UiPieChartData.Slice(value = 90f, color = Blue)
            )
        ),
        modifier = Modifier.size(350.dp)
    )
    UiLegendRows(
        isLightTheme = isLightTheme,
        items = listOf(
            UiLegendAmountItem(color = Red, title = "Red", percent = 10f, amount = 100.00),
            UiLegendAmountItem(color = Blue, title = "Blue", percent = 90f, amount = 900.00)
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ColumnScope.FirstContent(isLightTheme: Boolean, enabled: Boolean) {
    var alertDialogVisible by remember { mutableStateOf(false) }
    var bottomSheetVisible by remember { mutableStateOf(false) }
    var datePickerVisible by remember { mutableStateOf(false) }
    var dateRangePickerVisible by remember { mutableStateOf(false) }
    var timePickerVisible by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()

    var date by remember { mutableStateOf(LocalDate.now()) }
    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }
    var time by remember { mutableStateOf(LocalTime.now()) }
    var loadingVisible by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()


    UiButton(
        enabled = enabled,
        text = "Alert Dialog",
        onClick = { alertDialogVisible = true },
        modifier = Modifier.fillMaxWidth()
    )
    UiButton(
        enabled = enabled,
        text = "Bottom Sheet",
        onClick = { bottomSheetVisible = true },
        modifier = Modifier.fillMaxWidth()
    )
    UiButton(
        enabled = enabled,
        text = "Date Picker: $date",
        onClick = { datePickerVisible = true },
        modifier = Modifier.fillMaxWidth()
    )
    UiButton(
        enabled = enabled,
        text = "Date Range Picker: $startDate - $endDate",
        onClick = { dateRangePickerVisible = true },
        modifier = Modifier.fillMaxWidth()
    )
    UiButton(
        enabled = enabled,
        text = "Time Picker: $time",
        onClick = { timePickerVisible = true },
        modifier = Modifier.fillMaxWidth()
    )
    UiButton(
        enabled = enabled,
        text = "Loading (3s.)",
        onClick = {
            loadingVisible = true
            scope.launch {
                delay(3000)
                loadingVisible = false
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

    UiLoading(
        isVisible = loadingVisible,
        isLightTheme = isLightTheme
    )
    UiTimePickerDialog(
        isLightTheme = isLightTheme,
        isVisible = timePickerVisible,
        startTime = time,
        onDismissRequest = { timePickerVisible = false },
        onOkClick = {
            time = it
            timePickerVisible = false
        }
    )
    UiRangeDatePickerDialog(
        isLightTheme = isLightTheme,
        isVisible = dateRangePickerVisible,
        onDismissRequest = { dateRangePickerVisible = false },
        onOkClick = { start, end ->
            startDate = start
            endDate = end
            dateRangePickerVisible = false
        }
    )
    UiDatePickerDialog(
        isLightTheme = isLightTheme,
        isVisible = datePickerVisible,
        startDate = date,
        onDismissRequest = { datePickerVisible = false },
        onOkClick = {
            date = it
            datePickerVisible = false
        }
    )
    UiModalBottomSheet(
        isLightTheme = isLightTheme,
        isVisible = bottomSheetVisible,
        onDismissRequest = { bottomSheetVisible = false },
        sheetState = sheetState,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text("Привет!", color = blackOrWhiteColor(isLightTheme))
                Text("Это тестовый BottomSheet!", color = blackOrWhiteColor(isLightTheme))
            }
        }
    )
    UiAlertDialog(
        isLightTheme = isLightTheme,
        isVisible = alertDialogVisible,
        title = "Привет!",
        subtitle = "Это тестовый AlertDialog!",
        onDismissRequest = { alertDialogVisible = false },
        yesTitle = "Привет",
        cancelTitle = "Назад",
        cancelTitleColor = Red,
        yesTitleColor = Blue,
        onYesClick = { alertDialogVisible = false },
        onCancelClick = { alertDialogVisible = false }
    )
}

@Composable
private fun SecondContent(isLightTheme: Boolean) {
    var menu by remember { mutableStateOf("") }
    var textField by remember { mutableStateOf("") }
    var chooser by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    UiMenu(
        modifier = Modifier.fillMaxWidth(),
        items = listOf(
            "1",
            "2",
            "3",
            "Очень супер-супер-дупер-мупер-шмупер длинный текст пипец вообще какой длинные ужас но круто!"
        ),
        itemToString = { it },
        itemToLeadingIcon = null,
        isLightTheme = isLightTheme,
        value = menu,
        onItemClick = { menu = it },
        placeholderText = "Привет! Это Menu)",
        textFieldLeadingIcon = painterResource(Res.drawable.my_finance_menu),
        expanded = expanded,
        onExpandedChange = { expanded = it },
        textFieldLeadingIconContentDescription = ""
    )
    UiTextField(
        isLightTheme = isLightTheme,
        value = textField,
        onValueChange = { textField = it },
        modifier = Modifier.fillMaxWidth(),
        placeholderText = "Привет! Это textField)",
        leadingIcon = painterResource(Res.drawable.my_finance_menu),
        contentDescription = ""
    )
    UiChooser(
        onClick = {
            if (chooser == "") chooser = "LIZA!"
            else chooser = ""
        },
        isLightTheme = isLightTheme,
        value = chooser,
        modifier = Modifier.fillMaxWidth(),
        placeholderText = "Привет! Это chooser)",
        leadingIcon = painterResource(Res.drawable.my_finance_menu),
        trailingIcon = painterResource(Res.drawable.my_finance_menu),
        leadingIconContentDescription = "",
        trailingIconContentDescription = ""
    )
}

@Composable
private fun ColumnScope.ThirdContent(isLightTheme: Boolean) {
    UiDateAndAmountRow(
        modifier = Modifier.fillMaxWidth(),
        isLightTheme = isLightTheme,
        date = LocalDate.now(),
        amount = 10_000.00,
        isIncome = false
    )
    Text(text = "Currency Cards:")
    UiCurrencyCard(
        modifier = Modifier.fillMaxWidth(),
        isLightTheme = isLightTheme,
        currencyRub = CurrencyRub.Eur(amount = 90.03),
        percent = 1.41f
    )
    UiCurrencyCard(
        modifier = Modifier.fillMaxWidth(),
        isLightTheme = isLightTheme,
        currencyRub = CurrencyRub.Usd(amount = 85.40),
        percent = 0f
    )
    UiCurrencyCard(
        modifier = Modifier.fillMaxWidth(),
        isLightTheme = isLightTheme,
        currencyRub = CurrencyRub.Chf(amount = 11.42),
        percent = -1.32f
    )
    Text(text = "Expense Cards:")
    UiExpenseCard(
        modifier = Modifier.fillMaxWidth(),
        isLightTheme = isLightTheme,
        expense = Expense(
            id = 0,
            amount = 5_000.00,
            category = ExpenseCategory(id = 0, title = "CAFE"),
            date = LocalDate.now(),
            note = "Test"
        ),
        onClick = {}
    )
    UiExpenseCategoryCard(
        modifier = Modifier.fillMaxWidth(),
        isLightTheme = isLightTheme,
        category = "OTHER",
        enabled = true
    )
    Text("Incomes Cards:")
    UiIncomeCard(
        modifier = Modifier.fillMaxWidth(),
        isLightTheme = isLightTheme,
        income = Income(
            id = 0,
            amount = 15_000.00,
            category = IncomeCategory(id = 0, title = "BANK"),
            date = LocalDate.now(),
            note = "Test"
        ),
        onClick = {}
    )
    UiIncomeCategoryCard(
        modifier = Modifier.fillMaxWidth(),
        isLightTheme = isLightTheme,
        enabled = false,
        category = "SALARY"
    )
    Text("Reminder card:")
    UiReminderCard(
        modifier = Modifier.fillMaxWidth(),
        isLightTheme = isLightTheme,
        reminder = Reminder(
            id = 0,
            text = "Test",
            date = LocalDate.now(),
            time = LocalTime.now()
        ),
        onClick = {}
    )
}

@Composable
private fun bottomBarItems() = listOf(
    UiNavigationBarItem(
        icon = painterResource(Res.drawable.my_finance_incomes),
        contentDescription = "Incomes",
        route = "1",
        title = "Первая"
    ),
    UiNavigationBarItem(
        icon = painterResource(Res.drawable.my_finance_expenses),
        contentDescription = "Incomes",
        route = "2",
        title = "Вторая"
    ),
    UiNavigationBarItem(
        icon = painterResource(Res.drawable.my_finance_totals),
        contentDescription = "Incomes",
        route = "3",
        title = "Третья"
    ),
    UiNavigationBarItem(
        icon = painterResource(Res.drawable.my_finance_totals),
        contentDescription = "Incomes",
        route = "4",
        title = "Четвертая"
    ),
)