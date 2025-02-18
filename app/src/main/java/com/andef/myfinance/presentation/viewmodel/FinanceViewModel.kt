package com.andef.myfinance.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.andef.myfinance.domain.usecases.expense.GetFullExpenseByDayUseCase
import com.andef.myfinance.domain.usecases.expense.GetFullExpenseByPeriodUseCase
import com.andef.myfinance.domain.usecases.income.GetFullIncomeByDayUseCase
import com.andef.myfinance.domain.usecases.income.GetFullIncomeByPeriodUseCase
import javax.inject.Inject

class FinanceViewModel @Inject constructor(
    private val getFullIncomeByPeriodUseCase: GetFullIncomeByPeriodUseCase,
    private val getFullIncomeByDayUseCase: GetFullIncomeByDayUseCase,
    private val getFullExpenseByPeriodUseCase: GetFullExpenseByPeriodUseCase,
    private val getFullExpenseByDayUseCase: GetFullExpenseByDayUseCase
): ViewModel() {

}