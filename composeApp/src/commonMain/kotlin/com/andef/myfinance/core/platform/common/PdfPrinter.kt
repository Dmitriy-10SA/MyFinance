package com.andef.myfinance.core.platform.common

import kotlinx.datetime.LocalDate

interface PdfPrinter {
    fun printIncomePdf(
        incomes: List<Pair<LocalDate, Double>>,
        maxDate: LocalDate,
        minDate: LocalDate
    )

    fun printExpensePdf(
        expenses: List<Pair<LocalDate, Double>>,
        maxDate: LocalDate,
        minDate: LocalDate
    )
}