package com.andef.myfinance.core.platform

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

class AndroidLinkOpener(private val context: Context) : LinkOpener {
    override fun openLink(url: String) {
        Intent(Intent.ACTION_VIEW, url.toUri()).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(this)
        }
    }

    override fun openEmail(email: String) {
        Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:$email".toUri()
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.also { intent ->
            context.startActivity(Intent.createChooser(intent, "Выберите почтовый клиент"))
        }
    }
}