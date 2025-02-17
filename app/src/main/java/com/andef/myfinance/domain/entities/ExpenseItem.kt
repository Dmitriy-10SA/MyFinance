package com.andef.myfinance.domain.entities

data class ExpenseItem(
    val id: Int,
    val iconResId: Int,
    val price: Double,
    val comment: String,
    val dateString: String
)
