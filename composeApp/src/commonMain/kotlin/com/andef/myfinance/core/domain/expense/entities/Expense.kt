package com.andef.myfinance.core.domain.expense.entities

import com.andef.myfinance.core.domain.expense_category.entities.ExpenseCategory
import kotlinx.datetime.LocalDate

data class Expense(
    val id: Long,
    val amount: Double,
    val category: ExpenseCategory,
    val date: LocalDate,
    val note: String?
)