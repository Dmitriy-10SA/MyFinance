package com.andef.myfinance.core.platform.common

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter

class IosMoneyDecimalFormatter : MoneyDecimalFormatter {
    private val formatter: NSNumberFormatter = NSNumberFormatter().apply {
        numberStyle = 1u
        groupingSeparator = " "
        decimalSeparator = "."
        minimumFractionDigits = 2u
        maximumFractionDigits = 2u
    }

    override fun format(number: Double): String {
        return formatter.stringFromNumber(number as NSNumber) ?: number.toString()
    }
}