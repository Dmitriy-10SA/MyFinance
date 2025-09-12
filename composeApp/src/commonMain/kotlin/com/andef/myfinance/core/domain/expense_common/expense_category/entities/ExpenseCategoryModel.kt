package com.andef.myfinance.core.domain.expense_common.expense_category.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExpenseCategoryModel(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String
)
