package com.andef.myfinance.core.domain.income.entities

import com.andef.myfinance.core.domain.income_category.entities.IncomeCategoryModel
import kotlinx.datetime.LocalDate

data class IncomeModel(
    val id: Long,
    val amount: Double,
    val category: IncomeCategoryModel,
    val date: LocalDate,
    val note: String?
)