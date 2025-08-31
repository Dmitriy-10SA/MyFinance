package com.andef.myfinance.core.data.expense_common.expense.mapper

import com.andef.myfinance.core.domain.expense_common.expense.entities.ExpenseModel
import com.andef.myfinance.core.domain.expense_common.expense_category.entities.BaseExpenseCategory
import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel
import com.andef.myfinance.core.utils.mappers.localdate.intToLocalDate
import com.andef.myfinance.core.utils.mappers.localdate.localDateToInt
import com.andef.myfinance.db.Expense

class ExpenseMapper {
    fun toEntity(dbo: Expense): ExpenseModel = ExpenseModel(
        id = dbo.id,
        amount = dbo.amount,
        category = ExpenseCategoryModel(id = 0, title = dbo.category),
        date = intToLocalDate(dbo.date.toInt()),
        note = dbo.note
    )

    fun fromEntity(entity: ExpenseModel): Expense = Expense(
        id = entity.id,
        amount = entity.amount,
        category = getStringForDbo(entity.category.title),
        date = localDateToInt(entity.date).toLong(),
        note = entity.note
    )

    private fun getStringForDbo(string: String): String {
        return when (string) {
            BaseExpenseCategory.PRODUCTS.titleForUser -> BaseExpenseCategory.PRODUCTS.title
            BaseExpenseCategory.CAFE.titleForUser -> BaseExpenseCategory.CAFE.title
            BaseExpenseCategory.HOME.titleForUser -> BaseExpenseCategory.HOME.title
            BaseExpenseCategory.GIFTS.titleForUser -> BaseExpenseCategory.GIFTS.title
            BaseExpenseCategory.STUDY.titleForUser -> BaseExpenseCategory.STUDY.title
            BaseExpenseCategory.HEALTH.titleForUser -> BaseExpenseCategory.HEALTH.title
            BaseExpenseCategory.TRANSPORT.titleForUser -> BaseExpenseCategory.TRANSPORT.title
            BaseExpenseCategory.SPORT.titleForUser -> BaseExpenseCategory.SPORT.title
            BaseExpenseCategory.CLOTHES.titleForUser -> BaseExpenseCategory.CLOTHES.title
            BaseExpenseCategory.OTHER.titleForUser -> BaseExpenseCategory.OTHER.title
            else -> string
        }
    }
}