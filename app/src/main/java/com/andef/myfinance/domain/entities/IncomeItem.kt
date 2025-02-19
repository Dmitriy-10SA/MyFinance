package com.andef.myfinance.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IncomeItem(
    val id: Int,
    val iconResId: Int,
    val type: String,
    val income: Double,
    val comment: String,
    val dateString: String
) : Parcelable
