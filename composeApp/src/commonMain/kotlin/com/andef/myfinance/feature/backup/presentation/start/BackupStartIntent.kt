package com.andef.myfinance.feature.backup.presentation.start

import com.andef.myfinance.core.domain.backup.entities.BackupData

sealed class BackupStartIntent {
    data class RestoreData(
        val data: BackupData,
        val onSuccess: (String) -> Unit,
        val onError: (String) -> Unit
    ) : BackupStartIntent()

    data class HelpBottomSheetVisibleChange(val isVisible: Boolean) : BackupStartIntent()
}