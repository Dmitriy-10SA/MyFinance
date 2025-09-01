package com.andef.myfinance.feature.expense_common.expense_main.presentation

import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel
import kotlinx.datetime.LocalDate

sealed class ExpenseMainIntent {
    data class SubscribeForExpenses(
        val startDate: LocalDate,
        val endDate: LocalDate
    ) : ExpenseMainIntent()

    data class BottomSheetVisibleChange(
        val isVisible: Boolean,
        val date: LocalDate? = null,
        val category: ExpenseCategoryModel? = null,
        val amount: Double? = null,
        val id: Long? = null
    ) : ExpenseMainIntent()

    data class DeleteExpense(
        val id: Long,
        val onError: (String) -> Unit
    ) : ExpenseMainIntent()

    data class ChangeDeleteDialogVisible(val isVisible: Boolean) : ExpenseMainIntent()
}