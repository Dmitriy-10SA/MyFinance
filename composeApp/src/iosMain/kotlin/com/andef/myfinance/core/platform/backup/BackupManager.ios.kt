package com.andef.myfinance.core.platform.backup

import androidx.compose.runtime.Composable
import androidx.compose.ui.uikit.LocalUIViewController
import com.andef.myfinance.core.domain.backup.entities.BackupData
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.json.Json
import platform.Foundation.NSData
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.UIKit.UIDocumentPickerDelegateProtocol
import platform.UIKit.UIDocumentPickerMode
import platform.UIKit.UIDocumentPickerViewController
import platform.UIKit.UIViewController
import platform.darwin.NSObject
import kotlin.coroutines.resume

class IOSBackupManager : BackupManager {
    @OptIn(ExperimentalForeignApi::class)
    @Composable
    override fun pickBackupFile(onResult: (BackupData?) -> Unit): () -> Unit {
        val viewController = LocalUIViewController.current
        return {
            MainScope().launch {
                try {
                    val data = pickJsonFile(viewController)
                    onResult(data)
                } catch (_: Exception) {
                }
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    private suspend fun pickJsonFile(vc: UIViewController): BackupData? =
        suspendCancellableCoroutine { continuation ->
            val picker = UIDocumentPickerViewController(
                documentTypes = listOf("public.json"),
                inMode = UIDocumentPickerMode.UIDocumentPickerModeImport
            )

            var resumed = false
            fun safeResume(value: BackupData?) {
                if (!resumed && continuation.isActive) {
                    resumed = true
                    continuation.resume(value)
                }
            }

            val delegate = object : NSObject(), UIDocumentPickerDelegateProtocol {
                override fun documentPicker(
                    controller: UIDocumentPickerViewController,
                    didPickDocumentsAtURLs: List<*>
                ) {
                    val url = didPickDocumentsAtURLs.firstOrNull() as? NSURL
                    val nsData = url?.let { NSData.create(contentsOfURL = it) }
                    val content = nsData?.let {
                        @Suppress("CAST_NEVER_SUCCEEDS")
                        NSString.create(it, NSUTF8StringEncoding) as String
                    }

                    val parsed = try {
                        content?.let { Json.decodeFromString<BackupData>(it) }
                    } catch (_: Exception) {
                        null
                    }

                    safeResume(parsed)
                }

                override fun documentPickerWasCancelled(controller: UIDocumentPickerViewController) {
                    if (!resumed && continuation.isActive) {
                        resumed = true
                        continuation.resumeWith(Result.failure(Exception("Cancelled")))
                    }
                }
            }

            picker.delegate = delegate
            vc.presentViewController(picker, animated = true, completion = null)

            continuation.invokeOnCancellation {
                vc.dismissViewControllerAnimated(true, completion = null)
            }
        }
}