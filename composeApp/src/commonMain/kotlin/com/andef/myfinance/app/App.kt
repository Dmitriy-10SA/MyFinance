package com.andef.myfinance.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.andef.myfinance.core.domain.preferences.usecases.GetIsFirstStartUseCase
import com.andef.myfinance.core.domain.preferences.usecases.GetIsLightThemeAsFlowUseCase
import com.andef.myfinance.core.domain.preferences.usecases.GetIsLightThemeUseCase
import com.andef.myfinance.core.domain.preferences.usecases.GetUsernameAsFlowUseCase
import com.andef.myfinance.core.domain.preferences.usecases.GetUsernameUseCase
import com.andef.myfinance.core.navigation.graph.AppNavGraph
import com.andef.myfinance.core.navigation.routes.Screen
import com.andef.myfinance.core.navigation.routes.Screen.MainScreens.fabRoutes
import com.andef.myfinance.core.navigation.routes.Screen.MainScreens.mainRoutes
import com.kizitonwose.calendar.core.minusDays
import com.kizitonwose.calendar.core.minusMonths
import com.kizitonwose.calendar.core.minusYears
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_add
import myfinance.composeapp.generated.resources.my_finance_expenses
import myfinance.composeapp.generated.resources.my_finance_incomes
import myfinance.composeapp.generated.resources.my_finance_menu
import myfinance.composeapp.generated.resources.my_finance_totals
import network.chaintech.kmp_date_time_picker.utils.now
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.getKoin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val isLightTheme = GetIsLightThemeAsFlowUseCase(repository = getKoin().get())
        .invoke()
        .collectAsState(
            initial = GetIsLightThemeUseCase(repository = getKoin().get())
                .invoke(isSystemInDarkTheme = isSystemInDarkTheme())
        )
        .value
    val username = GetUsernameAsFlowUseCase(repository = getKoin().get())
        .invoke()
        .collectAsState(initial = GetUsernameUseCase(repository = getKoin().get()).invoke())
        .value
    val navHostController = rememberNavController()
    val navBackStackEntry = navHostController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var endDate by remember { mutableStateOf(LocalDate.now()) }
    var datePickerVisible by remember { mutableStateOf(false) }
    var lastSelectedTabIndex by remember { mutableIntStateOf(0) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    MyFinanceTheme(darkTheme = !isLightTheme) {
        AppDrawer(
            isLightTheme = isLightTheme,
            navHostController = navHostController,
            navBackStackEntry = navBackStackEntry,
            startDate = startDate,
            endDate = endDate,
            datePickerVisible = datePickerVisible,
            currentRoute = currentRoute,
            scope = scope,
            drawerState = drawerState,
            onDatesChoose = { s, e ->
                startDate = s
                endDate = e
                datePickerVisible = false
            },
            selectedTabIndex = selectedTabIndex,
            onDatesDismiss = {
                selectedTabIndex = lastSelectedTabIndex
                datePickerVisible = false
            },
            onTabClick = { tab ->
                if (tab.id != selectedTabIndex || tab.id == 4) {
                    selectedTabIndex = tab.id
                    when (tab.id) {
                        0 -> {
                            lastSelectedTabIndex = tab.id
                            startDate = LocalDate.now()
                            endDate = LocalDate.now()
                        }

                        1 -> {
                            lastSelectedTabIndex = tab.id
                            startDate = LocalDate.now().minusDays(7)
                            endDate = LocalDate.now()
                        }

                        2 -> {
                            lastSelectedTabIndex = tab.id
                            startDate = LocalDate.now().minusMonths(1)
                            endDate = LocalDate.now()
                        }

                        3 -> {
                            lastSelectedTabIndex = tab.id
                            startDate = LocalDate.now().minusYears(1)
                            endDate = LocalDate.now()
                        }

                        else -> datePickerVisible = true
                    }
                }
            }
        )
    }
}

@Composable
private fun AppDrawer(
    isLightTheme: Boolean,
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
                currentRoute = currentRoute
            )
        }
    )
}

@Composable
private fun AppDrawerContent(
    isLightTheme: Boolean,
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
            isFirstStart = GetIsFirstStartUseCase(repository = getKoin().get()).invoke(),
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
                    navHostController.navigate(Screen.IncomeAddScreen.route)
                }

                Screen.MainScreens.ExpenseMainScreen.route -> {
                    navHostController.navigate(Screen.ExpenseAddScreen.route)
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
                navHostController.navigate(item.route) {
                    popUpTo(Screen.MainScreens.IncomeMainScreen.route) {
                        saveState = true
                    }
                    restoreState = true
                    launchSingleTop = true
                }
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
    ),
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