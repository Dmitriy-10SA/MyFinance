package com.andef.myfinance.core.platform.backup

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.content.FileProvider
import com.andef.myfinance.core.domain.backup.entities.BackupData
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.time.LocalDate

class AndroidBackupManager(private val context: Context) : BackupManager {
    @Composable
    override fun pickBackupFile(onResult: (BackupData?) -> Unit): () -> Unit {
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

    override fun saveBackupFile(backupData: BackupData) {
        val json = Json.encodeToString(backupData)
        val fileName = "Мои_финансы_резервная_копия_${LocalDate.now()}.json"
        val file = File(context.cacheDir, fileName)
        file.writeText(json)

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/json"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(
            Intent.createChooser(
                intent,
                "Поделиться резервной копией"
            )
        )
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