package com.andef.myfinance.feature.income_common.income_add_and_change.presentation

import com.andef.myfinance.core.domain.income_common.income_category.entities.IncomeCategoryModel
import kotlinx.datetime.LocalDate

sealed class IncomeAddAndChangeIntent {
    data class ChangeAmount(val amount: Long?) : IncomeAddAndChangeIntent()
    data class ChangeCategory(val category: IncomeCategoryModel) : IncomeAddAndChangeIntent()
    data class ChangeNote(val note: String?) : IncomeAddAndChangeIntent()
    data class ChangeDate(val date: LocalDate) : IncomeAddAndChangeIntent()
    data class ChangeDatePickerVisible(val isVisible: Boolean) : IncomeAddAndChangeIntent()
    data class SaveClick(
        val onSuccess: () -> Unit,
        val onError: (String) -> Unit
    ) : IncomeAddAndChangeIntent()

    data class InitIncome(
        val incomeId: Long?,
        val onError: (String) -> Unit
    ) : IncomeAddAndChangeIntent()
}
