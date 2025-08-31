package com.andef.myfinance.core.domain.income_common.income_category.usecases

import com.andef.myfinance.core.domain.income_common.income_category.entities.IncomeCategoryModel
import com.andef.myfinance.core.domain.income_common.income_category.repository.IncomeCategoryRepository

class AddIncomeCategoryUseCase(private val repository: IncomeCategoryRepository) {
    suspend operator fun invoke(incomeCategoryModel: IncomeCategoryModel) =
        repository.addIncomeCategory(incomeCategoryModel)
}