package com.andef.myfinance.core.data.income_category.mapper

import com.andef.myfinance.core.domain.income_category.entities.IncomeCategoryModel
import com.andef.myfinance.db.Income_category

class IncomeCategoryMapper {
    fun toDbo(incomeCategoryModel: IncomeCategoryModel) = Income_category(
        id = incomeCategoryModel.id,
        title = incomeCategoryModel.title
    )

    fun toDomain(incomeCategoryDbo: Income_category) = IncomeCategoryModel(
        id = incomeCategoryDbo.id,
        title = incomeCategoryDbo.title
    )
}