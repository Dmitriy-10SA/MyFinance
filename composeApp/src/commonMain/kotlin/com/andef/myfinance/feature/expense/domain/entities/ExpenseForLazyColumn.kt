package com.andef.myfinance.feature.expense.domain.entities

import com.andef.myfinance.core.domain.expense.entities.Expense
import kotlinx.datetime.LocalDate

data class ExpenseForLazyColumn(
    val date: LocalDate,
    val totalAmount: Double,
    val expenses: List<Expense>
)