package com.andef.myfinance.app

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.andef.myfinance.core.design.MyFinanceTheme
import com.andef.myfinance.core.design.date.picker.ui.UiRangeDatePickerDialog
import com.andef.myfinance.core.design.fab.ui.UiFAB
import com.andef.myfinance.core.design.navbar.item.UiNavigationBarItem
import com.andef.myfinance.core.design.navbar.ui.UiNavigationBar
import com.andef.myfinance.core.design.scaffold.ui.UiScaffold
import com.andef.myfinance.core.design.topbar.type.UiTopBarTab
import com.andef.myfinance.core.design.topbar.type.UiTopBarType
import com.andef.myfinance.core.design.topbar.ui.UiTopBar
import com.andef.myfinance.core.navigation.graph.AppNavGraph
import com.andef.myfinance.core.navigation.routes.Screen
import com.andef.myfinance.core.navigation.routes.Screen.MainScreens.fabRoutes
import com.andef.myfinance.core.navigation.routes.Screen.MainScreens.mainRoutes
import com.andef.myfinance.core.navigation.utils.navigateWithSaveState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_add
import myfinance.composeapp.generated.resources.my_finance_expenses
import myfinance.composeapp.generated.resources.my_finance_incomes
import myfinance.composeapp.generated.resources.my_finance_menu
import myfinance.composeapp.generated.resources.my_finance_totals
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val viewModel = koinViewModel<AppViewModel>()
    val state = viewModel.state.collectAsState().value

    val navHostController = rememberNavController()
    val navBackStackEntry = navHostController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    MyFinanceTheme(darkTheme = !state.isLightTheme) {
        AppDrawer(
            isLightTheme = state.isLightTheme,
            navHostController = navHostController,
            navBackStackEntry = navBackStackEntry,
            startDate = state.startDate,
            endDate = state.endDate,
            datePickerVisible = state.datePickerVisible,
            currentRoute = currentRoute,
            scope = scope,
            drawerState = drawerState,
            onDatesChoose = { s, e ->
                viewModel.send(AppIntent.DatesChoose(s, e))
            },
            selectedTabIndex = state.selectedTabIndex,
            onDatesDismiss = { viewModel.send(AppIntent.DatesDismiss) },
            onTabClick = { tab -> viewModel.send(AppIntent.TabClick(tab)) },
            isFirstStart = state.isFirstStart
        )
    }
}

@Composable
private fun AppDrawer(
    isLightTheme: Boolean,
    isFirstStart: Boolean,
    navHostController: NavHostController,
    navBackStackEntry: NavBackStackEntry?,
    startDate: LocalDate,
    endDate: LocalDate,
    datePickerVisible: Boolean,
    selectedTabIndex: Int,
    currentRoute: String?,
    scope: CoroutineScope,
    drawerState: DrawerState,
    onDatesChoose: (LocalDate, LocalDate) -> Unit,
    onDatesDismiss: () -> Unit,
    onTabClick: (UiTopBarTab) -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            // TODO()
        },
        content = {
            AppDrawerContent(
                isLightTheme = isLightTheme,
                navHostController = navHostController,
                navBackStackEntry = navBackStackEntry,
                startDate = startDate,
                endDate = endDate,
                datePickerVisible = datePickerVisible,
                scope = scope,
                drawerState = drawerState,
                onDatesChoose = onDatesChoose,
                onDatesDismiss = onDatesDismiss,
                selectedTabIndex = selectedTabIndex,
                onTabClick = onTabClick,
                currentRoute = currentRoute,
                isFirstStart = isFirstStart
            )
        }
    )
}

@Composable
private fun AppDrawerContent(
    isLightTheme: Boolean,
    isFirstStart: Boolean,
    navHostController: NavHostController,
    navBackStackEntry: NavBackStackEntry?,
    startDate: LocalDate,
    endDate: LocalDate,
    datePickerVisible: Boolean,
    selectedTabIndex: Int,
    currentRoute: String?,
    scope: CoroutineScope,
    drawerState: DrawerState,
    onDatesChoose: (LocalDate, LocalDate) -> Unit,
    onDatesDismiss: () -> Unit,
    onTabClick: (UiTopBarTab) -> Unit
) {
    UiScaffold(
        isLightTheme = isLightTheme,
        floatingActionButton = { MainFAB(navHostController, currentRoute) },
        topBar = {
            MainTopBar(
                isLightTheme = isLightTheme,
                navBackStackEntry = navBackStackEntry,
                selectedTabIndex = selectedTabIndex,
                scope = scope,
                drawerState = drawerState,
                onTabClick = onTabClick
            )
        },
        bottomBar = { MainBottomBar(isLightTheme, navHostController, currentRoute) }
    ) { innerPadding ->
        AppNavGraph(
            isLightTheme = isLightTheme,
            navHostController = navHostController,
            paddingValues = innerPadding,
            isFirstStart = isFirstStart,
            startDate = startDate,
            endDate = endDate
        )
        UiRangeDatePickerDialog(
            isVisible = datePickerVisible,
            isLightTheme = isLightTheme,
            onDismissRequest = onDatesDismiss,
            onOkClick = { s, e -> onDatesChoose(s, e) }
        )
    }
}

@Composable
private fun MainFAB(navHostController: NavHostController, currentRoute: String?) {
    UiFAB(
        icon = painterResource(Res.drawable.my_finance_add),
        iconContentDescription = "Иконка плюса",
        isVisible = currentRoute in fabRoutes,
        onClick = {
            when (currentRoute) {
                Screen.MainScreens.IncomeMainScreen.route -> {
                    navHostController.navigateWithSaveState(
                        popUpToRoute = Screen.MainScreens.IncomeMainScreen.route,
                        whereNavigateRoute = Screen.IncomeAddScreen.route
                    )
                }

                Screen.MainScreens.ExpenseMainScreen.route -> {
                    navHostController.navigateWithSaveState(
                        popUpToRoute = Screen.MainScreens.ExpenseMainScreen.route,
                        whereNavigateRoute = Screen.ExpenseAddScreen.route
                    )
                }
            }
        }
    )
}

@Composable
private fun MainBottomBar(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    currentRoute: String?
) {
    UiNavigationBar(
        isLightTheme = isLightTheme,
        itemSelected = { item -> item.route == currentRoute },
        onItemClick = { item ->
            if (item.route != currentRoute) {
                navHostController.navigateWithSaveState(
                    popUpToRoute = Screen.MainScreens.IncomeMainScreen.route,
                    whereNavigateRoute = item.route
                )
            }
        },
        items = mainNavBarItems(),
        isVisible = currentRoute in mainRoutes
    )
}

@Composable
private fun mainNavBarItems() = listOf(
    UiNavigationBarItem(
        icon = painterResource(Res.drawable.my_finance_incomes),
        contentDescription = "Иконка доходов",
        route = Screen.MainScreens.IncomeMainScreen.route,
        title = "Доходы"
    ),
    UiNavigationBarItem(
        icon = painterResource(Res.drawable.my_finance_expenses),
        contentDescription = "Иконка расходов",
        route = Screen.MainScreens.ExpenseMainScreen.route,
        title = "Расходы"
    ),
    UiNavigationBarItem(
        icon = painterResource(Res.drawable.my_finance_totals),
        contentDescription = "Иконка итогов",
        route = Screen.MainScreens.TotalMainScreen.route,
        title = "Итоги"
    )
)

@Composable
private fun MainTopBar(
    isLightTheme: Boolean,
    navBackStackEntry: NavBackStackEntry?,
    selectedTabIndex: Int,
    scope: CoroutineScope,
    drawerState: DrawerState,
    onTabClick: (UiTopBarTab) -> Unit
) {
    UiTopBar(
        isLightTheme = isLightTheme,
        type = UiTopBarType.WithTabs(
            tabs = dateTabs,
            selectedTabIndex = selectedTabIndex,
            onTabClick = onTabClick
        ),
        title = "Мои финансы",
        navigationIcon = painterResource(Res.drawable.my_finance_menu),
        navigationIconContentDescription = "Меню",
        isVisible = navBackStackEntry?.destination?.route in mainRoutes,
        onNavigationIconClick = { scope.launch { drawerState.open() } }
    )
}

private val dateTabs = listOf(
    UiTopBarTab(id = 0, title = "День"),
    UiTopBarTab(id = 1, title = "Неделя"),
    UiTopBarTab(id = 2, title = "Месяц"),
    UiTopBarTab(id = 3, title = "Год"),
    UiTopBarTab(id = 4, title = "Период")
)