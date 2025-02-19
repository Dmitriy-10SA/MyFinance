package com.andef.myfinance.presentation.adapter.income

import com.andef.myfinance.domain.entities.IncomeItem

fun interface OnIncomeItemClickListeners {
    fun onClick(incomeItem: IncomeItem)
}