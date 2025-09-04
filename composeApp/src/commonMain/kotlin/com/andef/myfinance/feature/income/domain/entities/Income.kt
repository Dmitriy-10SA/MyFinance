package com.andef.myfinance.feature.income.domain.entities

import kotlinx.datetime.LocalDate

data class Income(
    val id: Long,
    val amount: Double,
    val category: IncomeCategory,
    val date: LocalDate,
    val note: String?
)