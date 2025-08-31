package com.andef.myfinance.core.platform.backup

import androidx.compose.runtime.Composable
import com.andef.myfinance.core.domain.backup.entities.BackupData

interface BackupManager {
    @Composable
    fun pickBackupFile(onResult: (BackupData?) -> Unit): () -> Unit
}