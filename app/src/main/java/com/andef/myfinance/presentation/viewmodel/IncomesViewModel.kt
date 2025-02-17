package com.andef.myfinance.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.andef.myfinance.domain.usecases.income.AddIncomeUseCase
import com.andef.myfinance.domain.usecases.income.GetIncomesByDayUseCase
import com.andef.myfinance.domain.usecases.income.GetIncomesByPeriodUseCase
import com.andef.myfinance.domain.usecases.income.RemoveIncomeUseCase
import javax.inject.Inject

class IncomesViewModel  @Inject constructor(
    private val addIncomeUseCase: AddIncomeUseCase,
    private val removeIncomeUseCase: RemoveIncomeUseCase,
    private val getIncomesByDayUseCase: GetIncomesByDayUseCase,
    private val getIncomesByPeriodUseCase: GetIncomesByPeriodUseCase
) : ViewModel() {
}