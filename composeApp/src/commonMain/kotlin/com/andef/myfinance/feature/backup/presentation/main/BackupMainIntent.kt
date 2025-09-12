package com.andef.myfinance.feature.backup.presentation.main

import com.andef.myfinance.core.domain.backup.entities.BackupData

sealed class BackupMainIntent {
    data class SaveData(
        val onSuccess: (BackupData) -> Unit,
        val onError: (String) -> Unit
    ) : BackupMainIntent()

    data class RestoreData(
        val data: BackupData,
        val onSuccess: (String) -> Unit,
        val onError: (String) -> Unit
    ) : BackupMainIntent()
}