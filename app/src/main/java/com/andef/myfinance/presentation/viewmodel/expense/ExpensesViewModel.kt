package com.andef.myfinance.presentation.viewmodel.expense

import androidx.lifecycle.ViewModel
import com.andef.myfinance.domain.usecases.expense.AddExpenseUseCase
import com.andef.myfinance.domain.usecases.expense.GetExpensesByDayUseCase
import com.andef.myfinance.domain.usecases.expense.GetExpensesByPeriodUseCase
import com.andef.myfinance.domain.usecases.expense.GetFullExpenseByDayUseCase
import com.andef.myfinance.domain.usecases.expense.GetFullExpenseByPeriodUseCase
import com.andef.myfinance.domain.usecases.expense.RemoveExpenseUseCase
import javax.inject.Inject

class ExpensesViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val removeExpenseUseCase: RemoveExpenseUseCase,
    private val getExpensesByDayUseCase: GetExpensesByDayUseCase,
    private val getExpensesByPeriodUseCase: GetExpensesByPeriodUseCase,
    private val getFullExpenseByDayUseCase: GetFullExpenseByDayUseCase,
    private val getFullExpenseByPeriodUseCase: GetFullExpenseByPeriodUseCase
) : ViewModel()