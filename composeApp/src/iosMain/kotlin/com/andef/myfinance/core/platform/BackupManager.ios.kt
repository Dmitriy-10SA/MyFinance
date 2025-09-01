package com.andef.myfinance.core.platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.uikit.LocalUIViewController
import com.andef.myfinance.core.domain.backup.entities.BackupData
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.json.Json
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.UIKit.UIDocumentPickerMode
import platform.UIKit.UIDocumentPickerViewController
import platform.darwin.NSObject
import kotlin.coroutines.resume

class IOSBackupManager : BackupManager {
    @OptIn(ExperimentalForeignApi::class)
    @Composable
    override fun pickBackupFile(onResult: (BackupData?) -> Unit) {
        val viewController = LocalUIViewController.current
        LaunchedEffect(Unit) {
            try {
                val data = pickJsonFile(viewController)
                onResult(data)
            } catch (_: Exception) {
                onResult(null)
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    private suspend fun pickJsonFile(vc: platform.UIKit.UIViewController): BackupData? =
        suspendCancellableCoroutine { continuation ->
            val picker = UIDocumentPickerViewController(
                documentTypes = listOf("public.json"),
                inMode = UIDocumentPickerMode.UIDocumentPickerModeImport
            )

            val delegate = object : NSObject(), platform.UIKit.UIDocumentPickerDelegateProtocol {
                override fun documentPicker(
                    controller: UIDocumentPickerViewController,
                    didPickDocumentsAtURLs: List<*>
                ) {
                    val url = didPickDocumentsAtURLs.firstOrNull()
                            as? platform.Foundation.NSURL ?: return continuation.resume(null)

                    val nsData: NSData? = NSData.create(contentsOfURL = url)
                    val content: String? = nsData?.let {
                        @Suppress("CAST_NEVER_SUCCEEDS")
                        NSString.create(it, NSUTF8StringEncoding) as String
                    }

                    val parsed = try {
                        content?.let { Json.decodeFromString<BackupData>(it) }
                    } catch (_: Exception) {
                        null
                    }

                    continuation.resume(parsed)
                }

                override fun documentPickerWasCancelled(controller: UIDocumentPickerViewController) {
                    continuation.resume(null)
                }
            }

            picker.delegate = delegate
            vc.presentViewController(picker, animated = true, completion = null)
        }
}