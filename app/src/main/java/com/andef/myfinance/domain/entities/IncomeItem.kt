package com.andef.myfinance.domain.entities

data class IncomeItem(
    val id: Int,
    val iconResId: Int,
    val income: Double,
    val comment: String,
    val dateString: String
)
