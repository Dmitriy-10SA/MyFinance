package com.andef.myfinance.core.platform.backup

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.andef.myfinance.core.domain.backup.entities.BackupData
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader

class AndroidBackupManager : BackupManager {
    @Composable
    override fun pickBackupFile(onResult: (BackupData?) -> Unit): () -> Unit {
        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument(),
            onResult = { uri: Uri? ->
                uri?.let {
                    val data = importDataFromJson(it, context)
                    onResult(data)
                }
            }
        )
        return { launcher.launch(arrayOf("application/json")) }
    }

    private fun importDataFromJson(uri: Uri, context: Context): BackupData? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val reader = BufferedReader(InputStreamReader(inputStream))
                val json = reader.readText()
                Json.decodeFromString<BackupData>(json)
            }
        } catch (_: Exception) {
            null
        }
    }
}