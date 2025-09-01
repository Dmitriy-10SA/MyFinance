package com.andef.myfinance.feature.auth.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.andef.myfinance.core.design.button.ui.UiButton
import com.andef.myfinance.core.design.snackbar.type.UiSnackbarType
import com.andef.myfinance.core.design.snackbar.ui.UiSnackbar
import com.andef.myfinance.core.design.textfield.ui.UiTextField
import com.andef.myfinance.core.navigation.routes.Screen
import com.andef.myfinance.core.utils.Blue
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.showSnackbar
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_dm_sans
import myfinance.composeapp.generated.resources.my_finance_person
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthScreen(
    isLightTheme: Boolean,
    paddingValues: PaddingValues,
    navHostController: NavHostController
) {
    val viewModel = koinViewModel<AuthViewModel>()
    val state = viewModel.state.collectAsState().value

    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()

    MainContent(
        isLightTheme = isLightTheme,
        username = state.username,
        onUsernameChange = { viewModel.send(AuthIntent.UsernameChange(it)) },
        onNextButtonClick = {
            viewModel.send(
                AuthIntent.NextClick(
                    onSuccess = { route ->
                        keyboardController?.hide()
                        navHostController.navigate(route) { popUpTo(0) }
                    },
                    onError = { msg ->
                        showSnackbar(scope, snackbarHostState, msg)
                    }
                )
            )
        },
        onBackupButtonClick = {
            navHostController.navigate(Screen.StartScreens.BackupStartScreen.route)
        },
        nextButtonEnabled = state.nextButtonEnabled,
        paddingValues = paddingValues
    )
    UiSnackbar(
        paddingValues = paddingValues,
        snackbarHostState = snackbarHostState,
        type = UiSnackbarType.Error
    )
}

@Composable
private fun MainContent(
    paddingValues: PaddingValues,
    isLightTheme: Boolean,
    username: String,
    onUsernameChange: (String) -> Unit,
    onBackupButtonClick: () -> Unit,
    nextButtonEnabled: Boolean,
    onNextButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding(), start = 12.dp, end = 12.dp)
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(6.dp))
        TitleAndTextField(
            isLightTheme = isLightTheme,
            username = username,
            onUsernameChange = onUsernameChange,
            nextButtonEnabled = nextButtonEnabled,
            onNextButtonClick = onNextButtonClick
        )
        BackupAndNextButton(
            onBackupButtonClick = onBackupButtonClick,
            onNextButtonClick = onNextButtonClick,
            nextButtonEnabled = nextButtonEnabled
        )
        Spacer(modifier = Modifier.height(6.dp))
    }
}

@Composable
private fun BackupAndNextButton(
    onBackupButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    nextButtonEnabled: Boolean
) {
    Text(
        modifier = Modifier
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onBackupButtonClick),
        text = "Восстановить данные",
        maxLines = 1,
        color = Blue,
        overflow = TextOverflow.Ellipsis,
        textDecoration = TextDecoration.Underline,
        textAlign = TextAlign.Start,
        fontSize = 14.sp
    )
    Spacer(modifier = Modifier.height(10.dp))
    UiButton(
        text = "Продолжить",
        onClick = onNextButtonClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = nextButtonEnabled
    )
}

@Composable
private fun TitleAndTextField(
    isLightTheme: Boolean,
    username: String,
    onUsernameChange: (String) -> Unit,
    nextButtonEnabled: Boolean,
    onNextButtonClick: () -> Unit
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = "Мои финансы",
        fontFamily = FontFamily(Font(Res.font.my_finance_dm_sans)),
        fontSize = 32.sp,
        color = blackOrWhiteColor(isLightTheme = isLightTheme),
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(24.dp))
    UiTextField(
        isLightTheme = isLightTheme,
        value = username,
        onValueChange = onUsernameChange,
        modifier = Modifier.fillMaxWidth(),
        placeholderText = "Ваше имя",
        leadingIcon = painterResource(Res.drawable.my_finance_person),
        contentDescription = "Иконка человечка",
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { if (nextButtonEnabled) onNextButtonClick() })
    )
}