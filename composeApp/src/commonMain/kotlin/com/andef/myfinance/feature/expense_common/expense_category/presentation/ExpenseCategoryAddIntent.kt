package com.andef.myfinance.feature.expense_common.expense_category.presentation

sealed class ExpenseCategoryAddIntent {
    data class AddExpenseCategory(
        val title: String,
        val onError: (String) -> Unit
    ) : ExpenseCategoryAddIntent()

    data class ChangeExpenseCategory(
        val id: Long,
        val oldTitle: String,
        val title: String,
        val onError: (String) -> Unit
    ) : ExpenseCategoryAddIntent()

    data class ChangeDeleteDialogVisible(val isVisible: Boolean) : ExpenseCategoryAddIntent()

    data class DeleteExpenseCategory(
        val id: Long,
        val title: String,
        val onError: (String) -> Unit
    ) : ExpenseCategoryAddIntent()

    data object SubscribeForExpenseCategories : ExpenseCategoryAddIntent()

    data class ChangeActionsDialogVisible(val isVisible: Boolean) : ExpenseCategoryAddIntent()

    data class ChangeCurrentExpenseCategoryId(val id: Long?) : ExpenseCategoryAddIntent()

    data class ChangeCurrentExpenseCategoryTitle(val title: String) : ExpenseCategoryAddIntent()

    data class ChangeOldTitle(val title: String) : ExpenseCategoryAddIntent()

    data class AddOrChangeExpenseCategoryDialogVisible(
        val isVisible: Boolean
    ) : ExpenseCategoryAddIntent()
}