package com.andef.myfinance.feature.income_common.income_main.presentation

import com.andef.myfinance.core.domain.income_common.income_category.entities.IncomeCategoryModel
import com.andef.myfinance.feature.income_common.income_main.domain.entities.IncomeForLazyColumn
import kotlinx.datetime.LocalDate

data class IncomeMainState(
    val incomesForLazyColumn: List<IncomeForLazyColumn> = emptyList(),
    val totalAmount: Long = 0L,
    val showBottomSheet: Boolean = false,
    val categoryInBottomSheet: IncomeCategoryModel? = null,
    val dateInBottomSheet: LocalDate? = null,
    val amountInBottomSheet: Long? = null,
    val idInBottomSheet: Long? = null,
    val deleteDialogVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val initialFirstVisibleItemIndex: Int = 0,
    val initialFirstVisibleItemScrollOffset: Int = 0
)
