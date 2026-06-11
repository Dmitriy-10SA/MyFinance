package com.andef.myfinance.core.domain.expense_common.expense.entities

import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel
import com.andef.myfinance.core.utils.formatters.numbers.MoneyAmountSerializer
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExpenseModel(
    @SerialName("id")
    val id: Long,
    @SerialName("amount")
    @Serializable(with = MoneyAmountSerializer::class)
    val amount: Long,
    @SerialName("category")
    val category: ExpenseCategoryModel,
    @SerialName("date")
    val date: LocalDate,
    @SerialName("note")
    val note: String? = null
)
