package com.andef.myfinance.feature.income_common.income_category.presentation

import com.andef.myfinance.core.domain.income_common.income_category.entities.IncomeCategoryModel

data class IncomeCategoryAddState(
    val currentIncomeCategoryId: Long? = null,
    val currentIncomeCategoryTitle: String = "",
    val oldTitle: String = "",
    val actionsDialogVisible: Boolean = false,
    val incomeCategories: List<IncomeCategoryModel> = emptyList(),
    val addOrChangeIncomeCategoryDialogVisible: Boolean = false,
    val addOrChangeIncomeCategoryButtonEnabled: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)