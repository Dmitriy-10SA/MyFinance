package com.andef.myfinance.presentation.adapter.expense

import com.andef.myfinance.domain.entities.ExpenseItem

fun interface OnExpenseItemClickListener {
    fun onClick(expenseItem: ExpenseItem)
}