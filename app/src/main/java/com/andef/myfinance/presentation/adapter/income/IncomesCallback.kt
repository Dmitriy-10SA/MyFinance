package com.andef.myfinance.presentation.adapter.income

import androidx.recyclerview.widget.DiffUtil
import com.andef.myfinance.domain.entities.IncomeItem

class IncomesCallback : DiffUtil.ItemCallback<IncomeItem>() {
    override fun areItemsTheSame(oldItem: IncomeItem, newItem: IncomeItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: IncomeItem, newItem: IncomeItem): Boolean {
        return oldItem == newItem
    }
}