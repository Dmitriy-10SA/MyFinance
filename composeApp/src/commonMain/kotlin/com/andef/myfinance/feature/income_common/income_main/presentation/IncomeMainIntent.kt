package com.andef.myfinance.feature.income_common.income_main.presentation

import com.andef.myfinance.core.domain.income_common.income_category.entities.IncomeCategoryModel
import kotlinx.datetime.LocalDate

sealed class IncomeMainIntent {
    data class SubscribeForIncomes(
        val startDate: LocalDate,
        val endDate: LocalDate
    ) : IncomeMainIntent()

    data class BottomSheetVisibleChange(
        val isVisible: Boolean,
        val date: LocalDate? = null,
        val category: IncomeCategoryModel? = null,
        val amount: Long? = null,
        val id: Long? = null
    ) : IncomeMainIntent()

    data class SaveScrollState(
        val initialFirstVisibleItemIndex: Int,
        val initialFirstVisibleItemScrollOffset: Int
    ) : IncomeMainIntent()

    data class DeleteIncome(
        val id: Long,
        val onError: (String) -> Unit
    ) : IncomeMainIntent()

    data class ChangeDeleteDialogVisible(val isVisible: Boolean) : IncomeMainIntent()
}
