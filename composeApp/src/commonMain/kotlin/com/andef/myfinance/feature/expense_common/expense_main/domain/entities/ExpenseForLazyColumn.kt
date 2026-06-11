package com.andef.myfinance.feature.expense_common.expense_main.domain.entities

import com.andef.myfinance.core.domain.expense_common.expense.entities.ExpenseModel
import kotlinx.datetime.LocalDate

data class ExpenseForLazyColumn(
    val date: LocalDate,
    val totalAmount: Long,
    val expenseModels: List<ExpenseModel>
)
