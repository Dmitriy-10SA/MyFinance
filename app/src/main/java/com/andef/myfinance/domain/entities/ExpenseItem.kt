package com.andef.myfinance.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExpenseItem(
    val id: Int,
    val iconResId: Int,
    val type: String,
    val price: Double,
    val comment: String,
    val dateString: String
) : Parcelable
