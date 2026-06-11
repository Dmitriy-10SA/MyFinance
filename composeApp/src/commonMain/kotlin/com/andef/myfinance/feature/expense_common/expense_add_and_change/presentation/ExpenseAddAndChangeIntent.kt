package com.andef.myfinance.feature.expense_common.expense_add_and_change.presentation

import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel
import kotlinx.datetime.LocalDate

sealed class ExpenseAddAndChangeIntent {
    data class ChangeAmount(val amount: Long?) : ExpenseAddAndChangeIntent()
    data class ChangeCategory(val category: ExpenseCategoryModel) : ExpenseAddAndChangeIntent()
    data class ChangeNote(val note: String?) : ExpenseAddAndChangeIntent()
    data class ChangeDate(val date: LocalDate) : ExpenseAddAndChangeIntent()
    data class ChangeDatePickerVisible(val isVisible: Boolean) : ExpenseAddAndChangeIntent()
    data class SaveClick(
        val onSuccess: () -> Unit,
        val onError: (String) -> Unit
    ) : ExpenseAddAndChangeIntent()

    data class InitExpense(
        val expenseId: Long?,
        val onError: (String) -> Unit
    ) : ExpenseAddAndChangeIntent()
}
