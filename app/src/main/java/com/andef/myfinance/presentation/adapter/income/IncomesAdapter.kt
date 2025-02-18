package com.andef.myfinance.presentation.adapter.income

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.andef.myfinance.R
import com.andef.myfinance.domain.entities.IncomeItem
import com.andef.myfinance.presentation.formatter.PriceAndIncomeFormatter

class IncomesAdapter : ListAdapter<IncomeItem, IncomesViewHolder>(IncomesCallback()) {
    private var onIncomeItemClickListeners: OnIncomeItemClickListeners? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.incomes_item,
            parent,
            false
        )
        return IncomesViewHolder(view)
    }

    override fun onBindViewHolder(holder: IncomesViewHolder, position: Int) {
        val incomeItem = getItem(position)
        holder.textViewIncomeOfIncomes.text = PriceAndIncomeFormatter.formatPrice(incomeItem.income)
        holder.textViewTypeOfIncomes.text = incomeItem.type
        holder.imageViewIncomesIcon.setImageResource(incomeItem.iconResId)
        holder.constraintLayoutIncomesItem.setOnClickListener {
            onIncomeItemClickListeners?.onClick(incomeItem)
        }
    }

    fun setOnIncomeItemClickListeners(onIncomeItemClickListeners: OnIncomeItemClickListeners) {
        this.onIncomeItemClickListeners = onIncomeItemClickListeners
    }
}