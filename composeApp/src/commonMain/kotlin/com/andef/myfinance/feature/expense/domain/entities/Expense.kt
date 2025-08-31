package com.andef.myfinance.feature.expense.domain.entities

import kotlinx.datetime.LocalDate

data class Expense(
    val id: Long,
    val amount: Double,
    val category: ExpenseCategory,
    val date: LocalDate,
    val note: String?
)