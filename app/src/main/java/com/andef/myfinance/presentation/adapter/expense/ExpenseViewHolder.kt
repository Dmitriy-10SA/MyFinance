package com.andef.myfinance.presentation.adapter.expense

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.andef.myfinance.R

class ExpenseViewHolder(itemView: View) : ViewHolder(itemView) {
    val constraintLayoutExpensesItem =
        itemView.findViewById<ConstraintLayout>(R.id.constraintLayoutExpensesItem)

    val imageViewExpenseIcon = itemView.findViewById<ImageView>(R.id.imageViewExpenseIcon)
    val textViewTypeOfExpense = itemView.findViewById<TextView>(R.id.textViewTypeOfExpense)
    val textViewPriceOfExpense = itemView.findViewById<TextView>(R.id.textViewPriceOfExpense)
}