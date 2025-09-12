package com.andef.myfinance.feature.income_common.income_category.presentation

sealed class IncomeCategoryAddIntent {
    data class AddIncomeCategory(
        val title: String,
        val onError: (String) -> Unit
    ) : IncomeCategoryAddIntent()

    data class ChangeIncomeCategory(
        val id: Long,
        val oldTitle: String,
        val title: String,
        val onError: (String) -> Unit
    ) : IncomeCategoryAddIntent()

    data class ChangeDeleteDialogVisible(val isVisible: Boolean) : IncomeCategoryAddIntent()

    data class DeleteIncomeCategory(
        val id: Long,
        val title: String,
        val onError: (String) -> Unit
    ) : IncomeCategoryAddIntent()

    data object SubscribeForIncomeCategories : IncomeCategoryAddIntent()

    data class ChangeActionsDialogVisible(val isVisible: Boolean) : IncomeCategoryAddIntent()

    data class ChangeCurrentIncomeCategoryId(val id: Long?) : IncomeCategoryAddIntent()

    data class ChangeCurrentIncomeCategoryTitle(val title: String) : IncomeCategoryAddIntent()

    data class ChangeOldTitle(val title: String) : IncomeCategoryAddIntent()

    data class AddOrChangeIncomeCategoryDialogVisible(
        val isVisible: Boolean
    ) : IncomeCategoryAddIntent()
}