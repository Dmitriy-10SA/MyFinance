package com.andef.myfinance.data.database.expense

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val iconResId: Int,
    val price: Double,
    val comment: String,
    val dateString: String
)