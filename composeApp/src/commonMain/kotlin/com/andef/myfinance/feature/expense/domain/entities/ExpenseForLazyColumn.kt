package com.andef.myfinance.feature.expense.domain.entities

import com.andef.myfinance.core.domain.expense_common.expense.entities.ExpenseModel
import kotlinx.datetime.LocalDate

data class ExpenseForLazyColumn(
    val date: LocalDate,
    val totalAmount: Double,
    val expens: List<ExpenseModel>
)