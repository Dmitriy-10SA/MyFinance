package com.andef.myfinance.core.domain.income.entities

import com.andef.myfinance.core.domain.income_category.entities.IncomeCategory
import kotlinx.datetime.LocalDate

data class Income(
    val id: Long,
    val amount: Double,
    val category: IncomeCategory,
    val date: LocalDate,
    val note: String?
)