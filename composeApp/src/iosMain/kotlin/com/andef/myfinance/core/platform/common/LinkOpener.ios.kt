package com.andef.myfinance.core.platform.common

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

class IOSLinkOpener : LinkOpener {
    override fun openLink(url: String) {
        NSURL.URLWithString(url)?.let { nsUrl ->
            UIApplication.sharedApplication.openURL(
                url = nsUrl,
                options = emptyMap<Any?, Any?>(),
                completionHandler = null
            )
        }
    }

    override fun openEmail(email: String) {
        val mailUrl = "mailto:$email"
        NSURL.URLWithString(mailUrl)?.let { nsUrl ->
            UIApplication.sharedApplication.openURL(
                url = nsUrl,
                options = emptyMap<Any?, Any?>(),
                completionHandler = null
            )
        }
    }
}