package com.andef.myfinance.presentation.adapter.expense

import androidx.recyclerview.widget.DiffUtil
import com.andef.myfinance.domain.entities.ExpenseItem

class ExpenseCallback : DiffUtil.ItemCallback<ExpenseItem>() {
    override fun areItemsTheSame(oldItem: ExpenseItem, newItem: ExpenseItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ExpenseItem, newItem: ExpenseItem): Boolean {
        return oldItem == newItem
    }
}