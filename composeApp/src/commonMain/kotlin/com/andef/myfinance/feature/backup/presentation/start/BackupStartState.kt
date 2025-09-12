package com.andef.myfinance.feature.backup.presentation.start

data class BackupStartState(
    val isErrorSnackbar: Boolean = true,
    val isLoading: Boolean = false,
    val helpBottomSheetVisible: Boolean = false
)
