package com.andef.myfinance.core.domain.expense_common.expense.entities

import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel
import kotlinx.datetime.LocalDate

data class ExpenseModel(
    val id: Long,
    val amount: Double,
    val category: ExpenseCategoryModel,
    val date: LocalDate,
    val note: String?
)