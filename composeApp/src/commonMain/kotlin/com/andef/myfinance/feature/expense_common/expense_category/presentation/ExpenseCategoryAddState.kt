package com.andef.myfinance.feature.expense_common.expense_category.presentation

import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel

data class ExpenseCategoryAddState(
    val currentExpenseCategoryId: Long? = null,
    val currentExpenseCategoryTitle: String = "",
    val oldTitle: String = "",
    val actionsDialogVisible: Boolean = false,
    val expenseCategories: List<ExpenseCategoryModel> = emptyList(),
    val addOrChangeExpenseCategoryDialogVisible: Boolean = false,
    val addOrChangeExpenseCategoryButtonEnabled: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)