package com.andef.myfinance.core.data.income.mapper

import com.andef.myfinance.core.domain.income.entities.IncomeModel
import com.andef.myfinance.core.domain.income_category.entities.BaseIncomeCategory
import com.andef.myfinance.core.domain.income_category.entities.IncomeCategoryModel
import com.andef.myfinance.core.utils.mappers.localdate.intToLocalDate
import com.andef.myfinance.core.utils.mappers.localdate.localDateToInt
import com.andef.myfinance.db.Income

class IncomeMapper {
    fun toEntity(dbo: Income): IncomeModel = IncomeModel(
        id = dbo.id,
        amount = dbo.amount,
        category = IncomeCategoryModel(id = 0, title = dbo.category),
        date = intToLocalDate(dbo.date.toInt()),
        note = dbo.note
    )

    fun fromEntity(entity: IncomeModel): Income = Income(
        id = entity.id,
        amount = entity.amount,
        category = getStringForDbo(entity.category.title),
        date = localDateToInt(entity.date).toLong(),
        note = entity.note
    )

    private fun getStringForDbo(string: String): String {
        return when (string) {
            BaseIncomeCategory.SALARY.titleForUser -> BaseIncomeCategory.SALARY.title
            BaseIncomeCategory.BANK.titleForUser -> BaseIncomeCategory.BANK.title
            BaseIncomeCategory.LUCK.titleForUser -> BaseIncomeCategory.LUCK.title
            BaseIncomeCategory.GIFTS.titleForUser -> BaseIncomeCategory.GIFTS.title
            BaseIncomeCategory.OTHER.titleForUser -> BaseIncomeCategory.OTHER.title
            else -> string
        }
    }
}