package com.andef.myfinance.data.mapper.income

import com.andef.myfinance.data.database.income.IncomeItemDbModel
import com.andef.myfinance.domain.entities.IncomeItem

object IncomeItemDbModelToIncomeItem {
    fun mapList(incomeItemDbModelList: List<IncomeItemDbModel>): List<IncomeItem> {
        return incomeItemDbModelList.map {
            map(it)
        }
    }

    private fun map(incomeItemDbModel: IncomeItemDbModel): IncomeItem {
        return IncomeItem(
            incomeItemDbModel.id,
            incomeItemDbModel.iconResId,
            incomeItemDbModel.income,
            incomeItemDbModel.comment,
            incomeItemDbModel.dateString
        )
    }
}