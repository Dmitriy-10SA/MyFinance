package com.andef.myfinance.presentation.adapter.income

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.andef.myfinance.R
import com.andef.myfinance.domain.entities.IncomeItem
import javax.inject.Inject

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
        val expenseItem = getItem(position)
        holder.textViewIncomeOfIncomes.text = "${expenseItem.income}"
        holder.textViewTypeOfIncomes.text = expenseItem.type
        holder.imageViewIncomesIcon.background =
            holder.itemView.context.getDrawable(R.drawable.green_circle)
        holder.constraintLayoutIncomesItem.setOnClickListener {
            onIncomeItemClickListeners?.onClick(expenseItem)
        }
    }

    fun setOnIncomeItemClickListeners(onIncomeItemClickListeners: OnIncomeItemClickListeners) {
        this.onIncomeItemClickListeners = onIncomeItemClickListeners
    }
}