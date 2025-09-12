package com.andef.myfinance.app

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.andef.myfinance.core.design.bottom.sheet.ui.UiConnectionModalBottomSheet
import com.andef.myfinance.core.design.bottom.sheet.ui.UiModalBottomSheet
import com.andef.myfinance.core.design.button.ui.UiButton
import com.andef.myfinance.core.design.drawer.sheet.ui.UiModalDrawerSheet
import com.andef.myfinance.core.design.drawer.sheet.ui.UiModalDrawerSheetInnerItem
import com.andef.myfinance.core.design.textfield.ui.UiTextField
import com.andef.myfinance.core.navigation.routes.Screen
import com.andef.myfinance.core.platform.common.InterstitialAdManager
import com.andef.myfinance.core.platform.common.LinkOpener
import com.andef.myfinance.core.utils.Black
import com.andef.myfinance.core.utils.DarkGray
import com.andef.myfinance.core.utils.GrayForDark
import com.andef.myfinance.core.utils.GrayForLight
import com.andef.myfinance.core.utils.White
import com.andef.myfinance.core.utils.blackOrWhiteColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_analytics
import myfinance.composeapp.generated.resources.my_finance_backup
import myfinance.composeapp.generated.resources.my_finance_baseline_dark
import myfinance.composeapp.generated.resources.my_finance_baseline_light
import myfinance.composeapp.generated.resources.my_finance_edit
import myfinance.composeapp.generated.resources.my_finance_expense_analys
import myfinance.composeapp.generated.resources.my_finance_feedback
import myfinance.composeapp.generated.resources.my_finance_icon
import myfinance.composeapp.generated.resources.my_finance_outline_dark
import myfinance.composeapp.generated.resources.my_finance_outline_light
import myfinance.composeapp.generated.resources.my_finance_person
import myfinance.composeapp.generated.resources.my_finance_reminder
import myfinance.composeapp.generated.resources.my_finance_ruble
import myfinance.composeapp.generated.resources.my_finance_trending_down
import myfinance.composeapp.generated.resources.my_finance_trending_up
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.getKoin

@OptIn(markerClass = [ExperimentalMaterial3Api::class])
@Composable
actual fun MainDrawerSheetContent(
    navHostController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    viewModel: AppViewModel,
    username: String,
    isLightTheme: Boolean,
    interstitialAdManager: InterstitialAdManager
) {
    val linkOpener = getKoin().get<LinkOpener>()
    val nameChangeSheetState = rememberModalBottomSheetState()
    val nameChangeSheetVisible = rememberSaveable { mutableStateOf(false) }
    var usernameValue by remember { mutableStateOf(username) }
    val feedbackSheetState = rememberModalBottomSheetState()
    val feedbackSheetVisible = rememberSaveable { mutableStateOf(false) }

    UiModalDrawerSheet(isLightTheme = isLightTheme, drawerState = drawerState) {
        InnerContent(
            isLightTheme = isLightTheme,
            username = username,
            navHostController = navHostController,
            scope = scope,
            drawerState = drawerState,
            nameChangeSheetVisible = nameChangeSheetVisible,
            feedbackSheetVisible = feedbackSheetVisible,
            viewModel = viewModel,
            interstitialAdManager = interstitialAdManager
        )
        UsernameChangeBottomSheet(
            onUsernameChange = { usernameValue = it },
            isLightTheme = isLightTheme,
            nameChangeSheetVisible = nameChangeSheetVisible,
            nameChangeSheetState = nameChangeSheetState,
            usernameValue = usernameValue,
            viewModel = viewModel
        )
        UiConnectionModalBottomSheet(
            isLightTheme = isLightTheme,
            isVisible = feedbackSheetVisible.value,
            onDismissRequest = { feedbackSheetVisible.value = false },
            sheetState = feedbackSheetState,
            text = "Нашли ошибку или есть предложения по улучшению? Напишите разработчику:",
            onTelegramClick = { linkOpener.openLink("https://t.me/dsemkin") },
            onMailClick = { linkOpener.openLink("mailto:semkin_dmitriy10@vk.com") }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UsernameChangeBottomSheet(
    isLightTheme: Boolean,
    nameChangeSheetVisible: MutableState<Boolean>,
    nameChangeSheetState: SheetState,
    usernameValue: String,
    onUsernameChange: (String) -> Unit,
    viewModel: AppViewModel
) {
    UiModalBottomSheet(
        isLightTheme = isLightTheme,
        isVisible = nameChangeSheetVisible.value,
        onDismissRequest = { nameChangeSheetVisible.value = false },
        sheetState = nameChangeSheetState
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
                value = usernameValue,
                onValueChange = onUsernameChange,
                modifier = Modifier.fillMaxWidth(),
                placeholderText = "Ваше имя",
                leadingIcon = painterResource(Res.drawable.my_finance_person),
                contentDescription = "Иконка человека",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
            UiButton(
                text = "Сохранить",
                onClick = {
                    nameChangeSheetVisible.value = false
                    viewModel.setUsernameUseCase.invoke(usernameValue)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = usernameValue.isNotEmpty()
            )
        }
    }
}

private const val BANNER_ID = "R-M-17151552-4"

@Composable
private fun InnerContent(
    isLightTheme: Boolean,
    username: String?,
    navHostController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    nameChangeSheetVisible: MutableState<Boolean>,
    feedbackSheetVisible: MutableState<Boolean>,
    viewModel: AppViewModel,
    interstitialAdManager: InterstitialAdManager
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        Icon(
            modifier = Modifier.size(100.dp),
            tint = blackOrWhiteColor(isLightTheme),
            painter = painterResource(Res.drawable.my_finance_icon),
            contentDescription = "Иконка приложения"
        )
        Spacer(modifier = Modifier.height(12.dp))
        UsernameContent(isLightTheme, username, nameChangeSheetVisible)
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = blackOrWhiteColor(isLightTheme).copy(alpha = 0.2f)
        )
        LazyColumn(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            item {
                UiModalDrawerSheetInnerItem(
                    isLightTheme = isLightTheme,
                    itemText = "Анализ доходов",
                    icon = painterResource(Res.drawable.my_finance_analytics),
                    iconContentDescription = "Иконка анализа доходов",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navHostController.navigate(Screen.IncomeAnalysisScreen.route)
                        }
                    }
                )
            }
            item {
                UiModalDrawerSheetInnerItem(
                    isLightTheme = isLightTheme,
                    itemText = "Категории доходов",
                    icon = painterResource(Res.drawable.my_finance_trending_up),
                    iconContentDescription = "Иконка категории доходов",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navHostController.navigate(Screen.IncomeCategoryAddScreen.route)
                        }
                    }
                )
            }
            item {
                UiModalDrawerSheetInnerItem(
                    isLightTheme = isLightTheme,
                    itemText = "Анализ расходов",
                    icon = painterResource(Res.drawable.my_finance_expense_analys),
                    iconContentDescription = "Иконка анализа расходов",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navHostController.navigate(Screen.ExpenseAnalysisScreen.route)
                        }
                    }
                )
            }
            item {
                UiModalDrawerSheetInnerItem(
                    isLightTheme = isLightTheme,
                    itemText = "Категории расходов",
                    icon = painterResource(Res.drawable.my_finance_trending_down),
                    iconContentDescription = "Иконка категории расходов",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navHostController.navigate(Screen.ExpenseCategoryAddScreen.route)
                        }
                    }
                )
            }
            item {
                UiModalDrawerSheetInnerItem(
                    isLightTheme = isLightTheme,
                    itemText = "Курс валют",
                    icon = painterResource(Res.drawable.my_finance_ruble),
                    iconContentDescription = "Иконка рубля",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navHostController.navigate(Screen.CurrencysScreen.route)
                        }
                    }
                )
            }
            item {
                UiModalDrawerSheetInnerItem(
                    isLightTheme = isLightTheme,
                    itemText = "Напоминания",
                    icon = painterResource(Res.drawable.my_finance_reminder),
                    iconContentDescription = "Иконка напоминаний",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navHostController.navigate(Screen.AllRemindersScreen.route)
                        }
                    }
                )
            }
            item {
                UiModalDrawerSheetInnerItem(
                    isLightTheme = isLightTheme,
                    icon = painterResource(Res.drawable.my_finance_backup),
                    iconContentDescription = "Иконка резервное копирование",
                    itemText = "Резервное копирование",
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            interstitialAdManager.showAd {
                                navHostController.navigate(Screen.BackupMainScreen.route)
                            }
                        }
                    }
                )
            }
            item {
                UiModalDrawerSheetInnerItem(
                    isLightTheme = isLightTheme,
                    icon = painterResource(Res.drawable.my_finance_feedback),
                    iconContentDescription = "Иконка почты",
                    itemText = "Обратная связь",
                    onClick = { feedbackSheetVisible.value = true }
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = blackOrWhiteColor(isLightTheme).copy(alpha = 0.2f)
        )
        Spacer(modifier = Modifier.height(12.dp))
        UiThemeContent(isLightTheme = isLightTheme, viewModel = viewModel)
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun ColumnScope.UsernameContent(
    isLightTheme: Boolean,
    username: String?,
    nameChangeSheetVisible: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            maxLines = 1,
            modifier = Modifier.weight(1f),
            overflow = TextOverflow.Ellipsis,
            text = username ?: "",
            fontSize = 22.sp,
            color = blackOrWhiteColor(isLightTheme)
        )
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent,
                contentColor = blackOrWhiteColor(isLightTheme)
            ),
            onClick = { nameChangeSheetVisible.value = true }
        ) {
            Icon(
                painter = painterResource(Res.drawable.my_finance_edit),
                contentDescription = "Карандаш (изменить)"
            )
        }
    }
}

@Composable
private fun ColumnScope.UiThemeContent(isLightTheme: Boolean, viewModel: AppViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .height(48.dp)
            .background(
                color = if (isLightTheme) {
                    GrayForLight.copy(alpha = 0.15f)
                } else {
                    GrayForDark.copy(alpha = 0.15f)
                },
                shape = RoundedCornerShape(16.dp)
            )
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .padding(end = 2.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isLightTheme) White else Color.Transparent,
                contentColor = blackOrWhiteColor(isLightTheme)
            ),
            border = BorderStroke(
                width = 1.dp,
                color = if (isLightTheme) {
                    GrayForLight.copy(alpha = 0.3f)
                } else {
                    Color.Transparent
                }
            ),
            onClick = {
                if (!isLightTheme) {
                    viewModel.setIsLightThemeUseCase.invoke(isLightTheme = true)
                }
            }
        ) {
            Icon(
                painter = if (isLightTheme) {
                    painterResource(Res.drawable.my_finance_baseline_light)
                } else {
                    painterResource(Res.drawable.my_finance_outline_light)
                },
                tint = if (isLightTheme) Black else GrayForDark,
                contentDescription = "Значок для светлой темы"
            )
        }
        Button(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .padding(start = 2.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isLightTheme) Color.Transparent else DarkGray,
                contentColor = blackOrWhiteColor(isLightTheme)
            ),
            onClick = {
                if (isLightTheme) {
                    viewModel.setIsLightThemeUseCase.invoke(isLightTheme = false)
                }
            },
            border = BorderStroke(
                width = 1.dp,
                color = if (isLightTheme) {
                    Color.Transparent
                } else {
                    GrayForDark.copy(alpha = 0.3f)
                }
            )
        ) {
            Icon(
                painter = if (isLightTheme) {
                    painterResource(Res.drawable.my_finance_outline_dark)
                } else {
                    painterResource(Res.drawable.my_finance_baseline_dark)
                },
                tint = if (isLightTheme) GrayForLight else White,
                contentDescription = "Значок для темной темы"
            )
        }
    }
}