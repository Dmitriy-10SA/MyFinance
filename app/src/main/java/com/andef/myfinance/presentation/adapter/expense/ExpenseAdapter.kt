package com.andef.myfinance.presentation.adapter.expense

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.andef.myfinance.R
import com.andef.myfinance.di.annotation.ApplicationScope
import com.andef.myfinance.domain.entities.ExpenseItem
import javax.inject.Inject

class ExpenseAdapter : ListAdapter<ExpenseItem, ExpenseViewHolder>(ExpenseCallback()) {
    private var onExpenseItemClickListener: OnExpenseItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.expenses_item,
            parent,
            false
        )
        return ExpenseViewHolder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expenseItem = getItem(position)
        holder.textViewPriceOfExpense.text = "${expenseItem.price}"
        holder.textViewTypeOfExpense.text = expenseItem.type
        holder.imageViewExpenseIcon.background =
            holder.itemView.context.getDrawable(R.drawable.green_circle)
        holder.constraintLayoutExpensesItem.setOnClickListener {
            onExpenseItemClickListener?.onClick(expenseItem)
        }
    }

    fun setOnExpenseItemClickListener(onExpenseItemClickListener: OnExpenseItemClickListener) {
        this.onExpenseItemClickListener = onExpenseItemClickListener
    }
}