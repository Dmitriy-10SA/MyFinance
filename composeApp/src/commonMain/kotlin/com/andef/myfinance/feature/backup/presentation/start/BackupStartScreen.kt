package com.andef.myfinance.feature.backup.presentation.start

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.andef.myfinance.core.design.button.ui.UiButton
import com.andef.myfinance.core.design.loading.ui.UiLoading
import com.andef.myfinance.core.design.scaffold.ui.UiScaffold
import com.andef.myfinance.core.design.snackbar.type.UiSnackbarType
import com.andef.myfinance.core.design.snackbar.ui.UiSnackbar
import com.andef.myfinance.core.design.topbar.type.UiTopBarType
import com.andef.myfinance.core.design.topbar.ui.UiTopBar
import com.andef.myfinance.core.domain.backup.entities.BackupData
import com.andef.myfinance.core.navigation.routes.Screen
import com.andef.myfinance.core.platform.BackupManager
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.grayColor
import com.andef.myfinance.core.utils.showSnackbar
import com.andef.myfinance.feature.backup.presentation.BackupHelpDownText
import kotlinx.coroutines.CoroutineScope
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_arrow_back
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackupStartScreen(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    backupManager: BackupManager
) {
    val viewModel = koinViewModel<BackupStartViewModel>()
    val state = viewModel.state.collectAsState().value

    val snackbarHostState = remember { SnackbarHostState() }
    val helpBottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    UiScaffold(
        isLightTheme = isLightTheme,
        topBar = {
            UiTopBar(
                isLightTheme = isLightTheme,
                type = UiTopBarType.Center,
                navigationIconTint = Blue,
                title = "Восстановление данных",
                navigationIcon = painterResource(Res.drawable.my_finance_arrow_back),
                navigationIconContentDescription = "Назад",
                onNavigationIconClick = navHostController::popBackStack
            )
        }
    ) { topBarPadding ->
        MainContent(
            topBarPadding = topBarPadding,
            isLightTheme = isLightTheme,
            onBackupClick = {
                viewModel.send(BackupStartIntent.StartPickerStateChange(true))
            },
            helpBottomSheetState = helpBottomSheetState,
            onHelpClick = {
                viewModel.send(
                    BackupStartIntent.HelpBottomSheetVisibleChange(true)
                )
            },
            onHelpDismissRequest = {
                viewModel.send(
                    BackupStartIntent.HelpBottomSheetVisibleChange(false)
                )
            },
            helpBottomSheetVisible = state.helpBottomSheetVisible,
            onMailClick = { TODO() },
            onTelegramClick = { TODO() }
        )
        if (state.startPicker) {
            backupManager.pickBackupFile { backupData ->
                onResultForPickBackupFile(
                    viewModel = viewModel,
                    backupData = backupData,
                    navHostController = navHostController,
                    scope = scope,
                    snackbarHostState = snackbarHostState
                )
            }
        }
        UiSnackbar(
            paddingValues = PaddingValues(
                top = topBarPadding.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            ),
            snackbarHostState = snackbarHostState,
            type = if (state.isErrorSnackbar) UiSnackbarType.Error else UiSnackbarType.Success
        )
    }
    UiLoading(isLightTheme = isLightTheme, isVisible = state.isLoading)
}

private fun onResultForPickBackupFile(
    viewModel: BackupStartViewModel,
    backupData: BackupData?,
    navHostController: NavHostController,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    viewModel.send(BackupStartIntent.StartPickerStateChange(false))
    if (backupData != null) {
        viewModel.send(
            BackupStartIntent.RestoreData(
                data = backupData,
                onSuccess = {
                    navHostController.navigate(Screen.MainScreens.IncomeMainScreen.route) {
                        popUpTo(0)
                    }
                },
                onError = { msg ->
                    showSnackbar(scope, snackbarHostState, msg)
                }
            )
        )
    } else {
        showSnackbar(
            scope,
            snackbarHostState,
            "Не удалось восстановить данные. Убедитесь, что выбран правильный файл."
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainContent(
    topBarPadding: PaddingValues,
    isLightTheme: Boolean,
    onBackupClick: () -> Unit,
    onHelpClick: () -> Unit,
    onHelpDismissRequest: () -> Unit,
    helpBottomSheetVisible: Boolean,
    helpBottomSheetState: SheetState,
    onTelegramClick: () -> Unit,
    onMailClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = topBarPadding.calculateTopPadding())
            .navigationBarsPadding()
            .imePadding()
    ) {
        ColumnContent(isLightTheme = isLightTheme, onBackupClick = onBackupClick)
        BackupHelpDownText(
            isLightTheme = isLightTheme,
            connectionBottomSheetVisible = helpBottomSheetVisible,
            onHelpClick = onHelpClick,
            onTelegramClick = onTelegramClick,
            onMailClick = onMailClick,
            onDismissRequest = onHelpDismissRequest,
            connectionBottomSheetState = helpBottomSheetState
        )
    }
}

@Composable
private fun ColumnScope.ColumnContent(isLightTheme: Boolean, onBackupClick: () -> Unit) {
    Column(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 12.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Уже пользовались приложением?",
            fontSize = 20.sp,
            color = blackOrWhiteColor(isLightTheme = isLightTheme),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Загрузите резервную копию, чтобы восстановить данные с другого устройства",
            fontSize = 14.sp,
            color = grayColor(isLightTheme = isLightTheme),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        UiButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Восстановить данные",
            onClick = onBackupClick
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
}