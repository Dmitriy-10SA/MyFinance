package com.andef.myfinance.core.platform

interface LinkOpener {
    fun openLink(url: String)
    fun openEmail(email: String)
}