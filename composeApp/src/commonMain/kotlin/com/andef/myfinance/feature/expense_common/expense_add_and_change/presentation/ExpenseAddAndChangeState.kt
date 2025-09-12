package com.andef.myfinance.feature.expense_common.expense_add_and_change.presentation

import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel
import kotlinx.datetime.LocalDate

data class ExpenseAddAndChangeState(
    val expenseId: Long? = null,
    val amount: Double? = null,
    val category: ExpenseCategoryModel? = null,
    val date: LocalDate? = null,
    val note: String? = null,
    val isLoading: Boolean = false,
    val datePickerVisible: Boolean = false,
    val saveButtonEnabled: Boolean = false,
    val expenseCategories: List<ExpenseCategoryModel> = emptyList(),
    val isAdd: Boolean = true
)
