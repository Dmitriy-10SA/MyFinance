package com.andef.myfinance.core.domain.income_common.income.entities

import com.andef.myfinance.core.domain.income_common.income_category.entities.IncomeCategoryModel
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IncomeModel(
    @SerialName("id")
    val id: Long,
    @SerialName("amount")
    val amount: Double,
    @SerialName("category")
    val category: IncomeCategoryModel,
    @SerialName("date")
    val date: LocalDate,
    @SerialName("note")
    val note: String? = null
)