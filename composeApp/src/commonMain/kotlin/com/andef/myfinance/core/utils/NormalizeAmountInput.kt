package com.andef.myfinance.core.utils

fun normalizeAmountInput(input: String): String {
    val cleaned = input
        .replace("\u00A0", "")
        .replace(" ", "")
        .replace(',', '.')

    val result = StringBuilder()
    var hasDecimalSeparator = false
    var decimalsCount = 0

    for (char in cleaned) {
        when {
            char.isDigit() -> {
                if (hasDecimalSeparator) {
                    if (decimalsCount < 2) {
                        result.append(char)
                        decimalsCount++
                    }
                } else {
                    result.append(char)
                }
            }

            char == '.' && !hasDecimalSeparator -> {
                hasDecimalSeparator = true

                if (result.isEmpty()) {
                    result.append('0')
                }

                result.append('.')
            }
        }
    }

    val raw = result.toString()
    if (raw.isEmpty()) return ""

    val parts = raw.split('.', limit = 2)

    val integerPart = parts[0]
    val decimalPart = parts.getOrNull(1)
    val hasDot = raw.contains('.')

    val normalizedIntegerPart = integerPart
        .trimStart('0')
        .ifEmpty { "0" }

    return if (hasDot) {
        "$normalizedIntegerPart.${decimalPart.orEmpty()}"
    } else {
        normalizedIntegerPart
    }
}