package com.andef.myfinance.core.domain.income_common.income.entities

import com.andef.myfinance.core.domain.income_common.income_category.entities.IncomeCategoryModel
import com.andef.myfinance.core.utils.formatters.numbers.MoneyAmountSerializer
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IncomeModel(
    @SerialName("id")
    val id: Long,
    @SerialName("amount")
    @Serializable(with = MoneyAmountSerializer::class)
    val amount: Long,
    @SerialName("category")
    val category: IncomeCategoryModel,
    @SerialName("date")
    val date: LocalDate,
    @SerialName("note")
    val note: String? = null
)
