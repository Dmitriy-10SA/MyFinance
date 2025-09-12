package com.andef.myfinance.core.domain.income_common.income_category.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IncomeCategoryModel(
    @SerialName("id")
    val id: Long,
    @SerialName("title")
    val title: String
)