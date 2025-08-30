package com.andef.myfinance.feature.income.domain.entities

import com.andef.myfinance.core.domain.income.entities.Income
import kotlinx.datetime.LocalDate

data class IncomeForLazyColumn(
    val date: LocalDate,
    val totalAmount: Double,
    val incomes: List<Income>,
)