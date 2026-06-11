package com.andef.myfinance.core.platform.common

import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalDate

interface PdfPrinter {
    fun printIncomePdf(
        incomes: List<Pair<LocalDate, Long>>,
        maxDate: LocalDate,
        minDate: LocalDate
    )

    fun printExpensePdf(
        expenses: List<Pair<LocalDate, Long>>,
        maxDate: LocalDate,
        minDate: LocalDate
    )
}

@Composable
expect fun getPdfPrinter(): PdfPrinter
