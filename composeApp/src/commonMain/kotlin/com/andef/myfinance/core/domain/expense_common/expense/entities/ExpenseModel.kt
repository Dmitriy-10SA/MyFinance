package com.andef.myfinance.core.domain.expense_common.expense.entities

import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExpenseModel(
    @SerialName("id")
    val id: Long,
    @SerialName("amount")
    val amount: Double,
    @SerialName("category")
    val category: ExpenseCategoryModel,
    @SerialName("date")
    val date: LocalDate,
    @SerialName("note")
    val note: String? = null
)