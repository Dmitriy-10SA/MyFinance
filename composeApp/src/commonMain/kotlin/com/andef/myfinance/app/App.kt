package com.andef.myfinance.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
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
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.navigateWithSaveState
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

    val isLightTheme = viewModel.getIsLightThemeAsFlowUseCase.invoke().collectAsState(
        viewModel.getIsLightThemeUseCase.invoke(isSystemInDarkTheme())
    ).value

    val navHostController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(navHostController) {
        navHostController.addOnDestinationChangedListener { _, destination, _ ->
            viewModel.send(AppIntent.CurrentRouteChange(route = destination.route))
        }
    }

    MyFinanceTheme(darkTheme = !isLightTheme) {
        AppDrawer(
            isLightTheme = isLightTheme,
            navHostController = navHostController,
            startDate = state.startDate,
            endDate = state.endDate,
            datePickerVisible = state.datePickerVisible,
            currentRoute = state.currentRoute,
            scope = scope,
            drawerState = drawerState,
            onDatesChoose = { s, e ->
                viewModel.send(AppIntent.DatesChoose(s, e))
            },
            selectedTabIndex = state.selectedTabIndex,
            onDatesDismiss = { viewModel.send(AppIntent.DatesDismiss) },
            onTabClick = { tab -> viewModel.send(AppIntent.TabClick(tab)) },
            isFirstStart = state.isFirstStart,
            onItemClick = { item ->
                if (item.route != state.currentRoute) {
                    navHostController.navigateWithSaveState(
                        popUpToRoute = Screen.MainScreens.IncomeMainScreen.route,
                        whereNavigateRoute = item.route
                    )
                }
            },
            viewModel = viewModel,
            username = state.username,
            onMainScreensLeftSwipe = { viewModel.send(AppIntent.LeftSwipe) },
            onMainScreensRightSwipe = { viewModel.send(AppIntent.RightSwipe) }
        )
    }
}

@Composable
private fun AppDrawer(
    isLightTheme: Boolean,
    isFirstStart: Boolean,
    navHostController: NavHostController,
    viewModel: AppViewModel,
    username: String,
    startDate: LocalDate,
    endDate: LocalDate,
    datePickerVisible: Boolean,
    selectedTabIndex: Int,
    currentRoute: String?,
    scope: CoroutineScope,
    drawerState: DrawerState,
    onDatesChoose: (LocalDate, LocalDate) -> Unit,
    onDatesDismiss: () -> Unit,
    onTabClick: (UiTopBarTab) -> Unit,
    onItemClick: (UiNavigationBarItem) -> Unit,
    onMainScreensLeftSwipe: () -> Unit,
    onMainScreensRightSwipe: () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            MainDrawerSheetContent(
                navHostController = navHostController,
                scope = scope,
                drawerState = drawerState,
                viewModel = viewModel,
                username = username,
                isLightTheme = isLightTheme
            )
        },
        content = {
            AppDrawerContent(
                isLightTheme = isLightTheme,
                navHostController = navHostController,
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
                isFirstStart = isFirstStart,
                onItemClick = onItemClick,
                onMainScreensLeftSwipe = onMainScreensLeftSwipe,
                onMainScreensRightSwipe = onMainScreensRightSwipe
            )
        }
    )
}

@Composable
private fun AppDrawerContent(
    isLightTheme: Boolean,
    isFirstStart: Boolean,
    navHostController: NavHostController,
    startDate: LocalDate,
    endDate: LocalDate,
    datePickerVisible: Boolean,
    selectedTabIndex: Int,
    currentRoute: String?,
    scope: CoroutineScope,
    drawerState: DrawerState,
    onDatesChoose: (LocalDate, LocalDate) -> Unit,
    onDatesDismiss: () -> Unit,
    onTabClick: (UiTopBarTab) -> Unit,
    onItemClick: (UiNavigationBarItem) -> Unit,
    onMainScreensLeftSwipe: () -> Unit,
    onMainScreensRightSwipe: () -> Unit
) {
    UiScaffold(
        isLightTheme = isLightTheme,
        floatingActionButton = { MainFAB(navHostController, currentRoute) },
        topBar = {
            MainTopBar(
                isLightTheme = isLightTheme,
                selectedTabIndex = selectedTabIndex,
                scope = scope,
                drawerState = drawerState,
                onTabClick = onTabClick,
                currentRoute = currentRoute
            )
        },
        bottomBar = {
            MainBottomBar(
                isLightTheme = isLightTheme,
                currentRoute = currentRoute,
                onItemClick = onItemClick
            )
        }
    ) { innerPadding ->
        AppNavGraph(
            isLightTheme = isLightTheme,
            navHostController = navHostController,
            paddingValues = innerPadding,
            isFirstStart = isFirstStart,
            startDate = startDate,
            endDate = endDate,
            currentRoute = currentRoute,
            mainScreenIsVisible = currentRoute in mainRoutes,
            onMainScreensLeftSwipe = onMainScreensLeftSwipe,
            onMainScreensRightSwipe = onMainScreensRightSwipe
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
    currentRoute: String?,
    onItemClick: (UiNavigationBarItem) -> Unit
) {
    UiNavigationBar(
        isLightTheme = isLightTheme,
        itemSelected = { item -> item.route == currentRoute },
        onItemClick = onItemClick,
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
    currentRoute: String?,
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
        navigationIconTint = blackOrWhiteColor(isLightTheme = isLightTheme),
        title = "Мои финансы",
        navigationIcon = painterResource(Res.drawable.my_finance_menu),
        navigationIconContentDescription = "Меню",
        isVisible = currentRoute in mainRoutes,
        onNavigationIconClick = { scope.launch { drawerState.open() } }
    )
}

private val dateTabs = listOf(
    UiTopBarTab(id = 0, title = "День"),
    UiTopBarTab(id = 1, title = "Неделя"),
    UiTopBarTab(id = 2, title = "Месяц"),
    UiTopBarTab(id = 3, title = "Полгода"),
    UiTopBarTab(id = 4, title = "Год"),
    UiTopBarTab(id = 5, title = "Период")
)