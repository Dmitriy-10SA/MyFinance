package com.andef.myfinance.data.database.income

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "incomes")
data class IncomeItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val iconResId: Int,
    val income: Double,
    val type: String,
    val comment: String,
    val dateString: String
)
