package com.andef.myfinance.core.platform.common

interface LinkOpener {
    fun openLink(url: String)
    fun openAppOrLink(appId: String, fallbackUrl: String)
    fun openEmail(email: String)
}