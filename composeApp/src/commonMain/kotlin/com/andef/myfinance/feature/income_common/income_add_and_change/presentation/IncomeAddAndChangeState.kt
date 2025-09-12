package com.andef.myfinance.feature.income_common.income_add_and_change.presentation

import com.andef.myfinance.core.domain.income_common.income_category.entities.IncomeCategoryModel
import kotlinx.datetime.LocalDate

data class IncomeAddAndChangeState(
    val incomeId: Long? = null,
    val amount: Double? = null,
    val category: IncomeCategoryModel? = null,
    val date: LocalDate? = null,
    val note: String? = null,
    val isLoading: Boolean = false,
    val datePickerVisible: Boolean = false,
    val saveButtonEnabled: Boolean = false,
    val incomeCategories: List<IncomeCategoryModel> = emptyList(),
    val isAdd: Boolean = true
)
