package com.andef.myfinance.core.platform.common

interface LinkOpener {
    fun openLink(url: String)
    fun openEmail(email: String)
}