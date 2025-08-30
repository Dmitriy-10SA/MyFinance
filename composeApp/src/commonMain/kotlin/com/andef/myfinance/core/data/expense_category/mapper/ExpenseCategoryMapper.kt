package com.andef.myfinance.core.data.expense_category.mapper

import com.andef.myfinance.core.domain.expense_category.entities.ExpenseCategoryModel
import com.andef.myfinance.db.Expense_category

class ExpenseCategoryMapper {
    fun toDbo(expenseCategory: ExpenseCategoryModel) = Expense_category(
        id = expenseCategory.id,
        title = expenseCategory.title
    )

    fun toDomain(expenseCategoryDbo: Expense_category) = ExpenseCategoryModel(
        id = expenseCategoryDbo.id,
        title = expenseCategoryDbo.title
    )
}