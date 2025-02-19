package com.andef.myfinance.presentation.adapter.income

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.andef.myfinance.R

class IncomesViewHolder(itemView: View) : ViewHolder(itemView) {
    val constraintLayoutIncomesItem =
        itemView.findViewById<ConstraintLayout>(R.id.constraintLayoutIncomesItem)

    val imageViewIncomesIcon = itemView.findViewById<ImageView>(R.id.imageViewIncomesIcon)
    val textViewTypeOfIncomes = itemView.findViewById<TextView>(R.id.textViewTypeOfIncomes)
    val textViewIncomeOfIncomes = itemView.findViewById<TextView>(R.id.textViewPriceOfIncomes)
}