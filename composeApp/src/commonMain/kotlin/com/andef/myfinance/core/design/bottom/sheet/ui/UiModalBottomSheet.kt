package com.andef.myfinance.core.design.bottom.sheet.ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.andef.myfinance.core.utils.blackOrWhiteColor
import com.andef.myfinance.core.utils.bottomSheetShape
import com.andef.myfinance.core.utils.darkGrayOrWhiteColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiModalBottomSheet(
    isLightTheme: Boolean,
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    contentWindowInsets: @Composable () -> WindowInsets = { BottomSheetDefaults.windowInsets },
    properties: ModalBottomSheetProperties = ModalBottomSheetDefaults.properties,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (isVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            modifier = modifier,
            sheetState = sheetState,
            shape = bottomSheetShape(),
            containerColor = darkGrayOrWhiteColor(isLightTheme = isLightTheme),
            contentColor = blackOrWhiteColor(isLightTheme = isLightTheme),
            properties = properties,
            contentWindowInsets = contentWindowInsets,
            content = content
        )
    }
}