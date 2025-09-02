package com.andef.myfinance.feature.expense_common.expense_main.presentation

import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel
import com.andef.myfinance.feature.expense_common.expense_main.domain.entities.ExpenseForLazyColumn
import kotlinx.datetime.LocalDate

data class ExpenseMainState(
    val expensesForLazyColumn: List<ExpenseForLazyColumn> = emptyList(),
    val totalAmount: Double = 0.0,
    val showBottomSheet: Boolean = false,
    val categoryInBottomSheet: ExpenseCategoryModel? = null,
    val dateInBottomSheet: LocalDate? = null,
    val amountInBottomSheet: Double? = null,
    val idInBottomSheet: Long? = null,
    val deleteDialogVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val initialFirstVisibleItemIndex: Int = 0,
    val initialFirstVisibleItemScrollOffset: Int = 0
)