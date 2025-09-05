package com.andef.myfinance.feature.backup.presentation.main

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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
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
import com.andef.myfinance.core.platform.backup.BackupManager
import com.andef.myfinance.core.platform.common.LinkOpener
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
fun BackupMainScreen(
    isLightTheme: Boolean,
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    backupManager: BackupManager,
    linkOpener: LinkOpener
) {
    val viewModel = koinViewModel<BackupMainViewModel>()
    val state = viewModel.state.collectAsState()

    val helpBottomSheetState = rememberModalBottomSheetState()
    val helpBottomSheetVisible = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val launcher = backupManager.pickBackupFile { backupData ->
        onResultForPickBackupFile(
            viewModel = viewModel,
            backupData = backupData,
            scope = scope,
            snackbarHostState = snackbarHostState
        )
    }

    UiScaffold(
        isLightTheme = isLightTheme,
        topBar = {
            UiTopBar(
                isLightTheme = isLightTheme,
                type = UiTopBarType.Center,
                title = "Резервное копирование",
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
                type = if (state.value.isErrorSnackbar) {
                    UiSnackbarType.Error
                } else {
                    UiSnackbarType.Success
                }
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
                viewModel = viewModel,
                scope = scope,
                snackbarHostState = snackbarHostState,
                backupManager = backupManager,
                onBackupClick = { launcher() }
            )
            BackupHelpDownText(
                isLightTheme = isLightTheme,
                onHelpClick = { helpBottomSheetVisible.value = true },
                connectionBottomSheetVisible = helpBottomSheetVisible.value,
                connectionBottomSheetState = helpBottomSheetState,
                onDismissRequest = { helpBottomSheetVisible.value = false },
                onTelegramClick = { linkOpener.openLink("https://t.me/dsemkin") },
                onMailClick = { linkOpener.openLink("mailto:semkin_dmitriy10@vk.com") }
            )
        }
    }
    UiLoading(isLightTheme = isLightTheme, isVisible = state.value.isLoading)
}

private fun onResultForPickBackupFile(
    viewModel: BackupMainViewModel,
    backupData: BackupData?,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    if (backupData != null) {
        viewModel.send(
            BackupMainIntent.RestoreData(
                data = backupData,
                onSuccess = { msg -> showSnackbar(scope, snackbarHostState, msg) },
                onError = { msg -> showSnackbar(scope, snackbarHostState, msg) }
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

@Composable
private fun ColumnScope.MainContent(
    isLightTheme: Boolean,
    viewModel: BackupMainViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    backupManager: BackupManager,
    onBackupClick: () -> Unit
) {
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
            text = "Не теряйте данные о финансах",
            fontSize = 20.sp,
            color = blackOrWhiteColor(isLightTheme),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Сохраните резервную копию, чтобы восстановить информацию о финансах при смене " +
                    "телефона или очистке данных приложения. Восстанавливать данные не нужно, " +
                    "если вы не меняли телефон и не очищали данные, иначе текущие данные могут " +
                    "быть удалены",
            fontSize = 14.sp,
            color = grayColor(isLightTheme),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        UiButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Сохранить копию",
            onClick = { onSaveData(viewModel, scope, snackbarHostState, backupManager) }
        )
        Spacer(modifier = Modifier.height(12.dp))
        UiButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Восстановить данные",
            onClick = onBackupClick
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
}

private fun onSaveData(
    viewModel: BackupMainViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    backupManager: BackupManager
) {
    viewModel.send(
        BackupMainIntent.SaveData(
            onSuccess = { backupData ->
                try {
                    backupManager.saveBackupFile(backupData)
                } catch (_: Exception) {
                    showSnackbar(scope, snackbarHostState, "Ошибка! Попробуйте ещё раз!")
                }
            },
            onError = { msg -> showSnackbar(scope, snackbarHostState, msg) }
        )
    )
}