package com.andef.myfinance.feature.income.domain.entities

import com.andef.myfinance.core.domain.income_common.income.entities.IncomeModel
import kotlinx.datetime.LocalDate

data class IncomeForLazyColumn(
    val date: LocalDate,
    val totalAmount: Double,
    val incomeModels: List<IncomeModel>,
)