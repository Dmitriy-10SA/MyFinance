package com.andef.myfinance.data.mapper.income

import com.andef.myfinance.data.database.income.IncomeItemDbModel
import com.andef.myfinance.domain.entities.IncomeItem

object IncomeItemToIncomeItemDbModel {
    fun map(incomeItem: IncomeItem): IncomeItemDbModel {
        return IncomeItemDbModel(
            incomeItem.id,
            incomeItem.iconResId,
            incomeItem.income,
            incomeItem.comment,
            incomeItem.dateString
        )
    }
}