package com.andef.myfinance.feature.reminder_common.reminder_all.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.andef.myfinance.core.design.ads.type.UiAdsType
import com.andef.myfinance.core.design.ads.ui.UiAds
import com.andef.myfinance.core.design.alert.dialog.ui.UiAlertDialog
import com.andef.myfinance.core.design.bottom.sheet.ui.UiModalBottomSheet
import com.andef.myfinance.core.design.button.ui.UiButton
import com.andef.myfinance.core.design.card.reminder.ui.UiReminderCard
import com.andef.myfinance.core.design.fab.ui.UiFAB
import com.andef.myfinance.core.design.loading.ui.UiLoading
import com.andef.myfinance.core.design.scaffold.ui.UiScaffold
import com.andef.myfinance.core.design.snackbar.type.UiSnackbarType
import com.andef.myfinance.core.design.snackbar.ui.UiSnackbar
import com.andef.myfinance.core.design.topbar.type.UiTopBarType
import com.andef.myfinance.core.design.topbar.ui.UiTopBar
import com.andef.myfinance.core.navigation.routes.Screen
import com.andef.myfinance.core.platform.common.PermissionManager
import com.andef.myfinance.core.platform.common.SettingsOpener
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.Red
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.darkGrayOrWhiteColor
import com.andef.myfinance.core.utils.formatters.datetime.formatLocalDate
import com.andef.myfinance.core.utils.formatters.datetime.formatLocalTime
import com.andef.myfinance.core.utils.getters.minusDays
import com.andef.myfinance.core.utils.getters.now
import com.andef.myfinance.core.utils.getters.plusDays
import com.andef.myfinance.core.utils.grayColor
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_add
import myfinance.composeapp.generated.resources.my_finance_arrow_back
import myfinance.composeapp.generated.resources.my_finance_delete
import myfinance.composeapp.generated.resources.my_finance_edit
import myfinance.composeapp.generated.resources.my_finance_notification_perm
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllRemindersScreen(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    settingsOpener: SettingsOpener,
    permissionManager: PermissionManager
) {
    val viewModel = koinViewModel<AllRemindersViewModel>()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) { viewModel.send(AllRemindersIntent.SubscribeToReminders) }

    val reminderSheet = rememberModalBottomSheetState()
    val permissionsSheet = rememberModalBottomSheetState()
    val weekCalendarState = rememberWeekCalendarState(
        startDate = LocalDate.now().minusDays(7),
        endDate = LocalDate.now().plusDays(21),
        firstVisibleWeekDate = LocalDate.now(),
        firstDayOfWeek = DayOfWeek.MONDAY
    )
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val permissionsGranted = permissionManager.remindersGranted.collectAsState().value

    OnResume { permissionManager.refreshRemindersPermissions() }

    UiScaffold(
        isLightTheme = isLightTheme,
        topBar = {
            UiTopBar(
                isLightTheme = isLightTheme,
                type = UiTopBarType.WithCalendar(
                    weekCalendarState = weekCalendarState,
                    currentDay = state.value.currentDate,
                    onDayClick = { viewModel.send(AllRemindersIntent.DateSelected(it)) },
                    withEvent = { state.value.remindersLocalDatesForScreenAsSet.contains(it) }
                ),
                title = "Напоминания",
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
        },
        floatingActionButton = {
            UiFAB(
                onClick = { navHostController.navigate(Screen.ReminderAddScreen.route) },
                icon = painterResource(Res.drawable.my_finance_add),
                iconContentDescription = "Иконка добавить",
                isVisible = !state.value.isLoading
            )
        },
        bottomBar = {
            UiAds(
                isLightTheme = isLightTheme,
                id = BANNER_ID,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars),
                type = UiAdsType.StickyBanner,
                contextTags = contextTags
            )
        }
    ) { topBarPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(topBarPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = state.value.remindersForScreenAsList, key = { it.id }) { reminder ->
                UiReminderCard(
                    onClick = {
                        viewModel.send(
                            AllRemindersIntent.ReminderBottomSheetVisibleChange(
                                isVisible = true,
                                reminderId = reminder.id,
                                reminderText = reminder.text,
                                reminderDate = reminder.date,
                                reminderTime = reminder.time
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(),
                    isLightTheme = isLightTheme,
                    reminderModel = reminder
                )
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
        }
    }
    UiLoading(isVisible = state.value.isLoading, isLightTheme = isLightTheme)
    UiAlertDialog(
        isLightTheme = isLightTheme,
        isVisible = state.value.isError,
        title = "Ошибка!",
        subtitle = "Что-то пошло не так. Пожалуйста, попробуйте повторить",
        onDismissRequest = navHostController::popBackStack,
        yesTitle = "Повторить",
        cancelTitle = "Выйти",
        cancelTitleColor = Red,
        yesTitleColor = Blue,
        onYesClick = { viewModel.send(AllRemindersIntent.SubscribeToReminders) },
        onCancelClick = navHostController::popBackStack
    )
    PermissionsBottomSheet(
        isLightTheme = isLightTheme,
        permissionsSheetState = permissionsSheet,
        navHostController = navHostController,
        isVisible = !permissionsGranted,
        settingsOpener = settingsOpener
    )
    ReminderBottomSheet(
        isLightTheme = isLightTheme,
        reminderSheetState = reminderSheet,
        viewModel = viewModel,
        state = state,
        navHostController = navHostController
    )
    DeleteDialog(
        isLightTheme = isLightTheme,
        viewModel = viewModel,
        state = state,
        scope = scope,
        snackbarHostState = snackbarHostState
    )
}

private const val BANNER_ID = "R-M-17151552-6"
private val contextTags = listOf("напоминания", "бюджет")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PermissionsBottomSheet(
    isLightTheme: Boolean,
    permissionsSheetState: SheetState,
    navHostController: NavHostController,
    isVisible: Boolean,
    settingsOpener: SettingsOpener
) {
    UiModalBottomSheet(
        onDismissRequest = {
            navHostController.popBackStack()
        },
        sheetState = permissionsSheetState,
        isLightTheme = isLightTheme,
        isVisible = isVisible
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = 6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                painter = painterResource(Res.drawable.my_finance_notification_perm),
                contentScale = ContentScale.Crop,
                contentDescription = "Фото для напоминаний"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Необходимо разрешение на уведомления",
                fontSize = 16.sp,
                color = grayColor(isLightTheme)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                UiButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Разрешить",
                    onClick = settingsOpener::openSettings
                )
                TextButton(
                    onClick = {
                        navHostController.popBackStack()
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = darkGrayOrWhiteColor(isLightTheme),
                        contentColor = grayColor(isLightTheme)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 2.dp, vertical = 11.dp),
                        text = "Назад",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReminderBottomSheet(
    isLightTheme: Boolean,
    reminderSheetState: SheetState,
    navHostController: NavHostController,
    viewModel: AllRemindersViewModel,
    state: State<AllRemindersState>
) {
    UiModalBottomSheet(
        onDismissRequest = {
            viewModel.send(AllRemindersIntent.ReminderBottomSheetVisibleChange(isVisible = false))
        },
        sheetState = reminderSheetState,
        isLightTheme = isLightTheme,
        isVisible = state.value.reminderSheetVisible
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
                    text = state.value.reminderTextInBottomSheet ?: "",
                    fontSize = 16.sp,
                    color = blackOrWhiteColor(isLightTheme)
                )
                Text(
                    text = "${formatLocalDate(state.value.reminderDateInBottomSheet ?: LocalDate.now())} - ${
                        formatLocalTime(state.value.reminderTimeInBottomSheet ?: LocalTime.now())
                    }",
                    fontSize = 14.sp,
                    color = grayColor(isLightTheme)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(
                        onClick = {
                            navHostController.navigate(
                                Screen.ReminderScreen.passId(
                                    id = state.value.reminderIdInBottomSheet
                                        ?: throw IllegalArgumentException()
                                )
                            )
                            viewModel.send(
                                AllRemindersIntent.ReminderBottomSheetVisibleChange(
                                    isVisible = false
                                )
                            )
                        }
                    )
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
                    .clickable(
                        onClick = {
                            viewModel.send(AllRemindersIntent.DeleteDialogVisibleChange(true))
                        }
                    )
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
    viewModel: AllRemindersViewModel,
    state: State<AllRemindersState>,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    UiAlertDialog(
        isLightTheme = isLightTheme,
        title = "Удаление напоминания",
        subtitle = "Вы уверены? Данное действие невозможно отменить!",
        yesTitle = "Удалить",
        cancelTitle = "Отмена",
        cancelTitleColor = Blue,
        yesTitleColor = Red,
        onDismissRequest = {
            viewModel.send(AllRemindersIntent.DeleteDialogVisibleChange(isVisible = false))
        },
        onYesClick = {
            viewModel.send(AllRemindersIntent.DeleteDialogVisibleChange(isVisible = false))
            val reminderId = state.value.reminderIdInBottomSheet
            viewModel.send(AllRemindersIntent.ReminderBottomSheetVisibleChange(isVisible = false))
            viewModel.send(
                AllRemindersIntent.DeleteReminder(
                    id = reminderId ?: throw IllegalArgumentException(),
                    onError = { msg ->
                        scope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()
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
            viewModel.send(AllRemindersIntent.DeleteDialogVisibleChange(isVisible = false))
        },
        isVisible = state.value.deleteDialogVisible
    )
}

@Composable
private fun OnResume(action: () -> Unit) {
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                action()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
}