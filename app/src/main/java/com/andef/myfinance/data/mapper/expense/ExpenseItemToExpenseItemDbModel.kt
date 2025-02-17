package com.andef.myfinance.data.mapper.expense

import com.andef.myfinance.data.database.expense.ExpenseItemDbModel
import com.andef.myfinance.domain.entities.ExpenseItem

object ExpenseItemToExpenseItemDbModel {
    fun map(expenseItem: ExpenseItem): ExpenseItemDbModel {
        return ExpenseItemDbModel(
            expenseItem.id,
            expenseItem.iconResId,
            expenseItem.price,
            expenseItem.comment,
            expenseItem.dateString
        )
    }
}