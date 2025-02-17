package com.andef.myfinance.data.mapper.expense

import com.andef.myfinance.data.database.expense.ExpenseItemDbModel
import com.andef.myfinance.domain.entities.ExpenseItem

object ExpenseItemDbModelToExpenseItem {
    fun mapList(expenseItemDbModelList: List<ExpenseItemDbModel>): List<ExpenseItem> {
        return expenseItemDbModelList.map {
            map(it)
        }
    }

    private fun map(expenseItemDbModel: ExpenseItemDbModel): ExpenseItem {
        return ExpenseItem(
            expenseItemDbModel.id,
            expenseItemDbModel.iconResId,
            expenseItemDbModel.price,
            expenseItemDbModel.comment,
            expenseItemDbModel.dateString
        )
    }
}