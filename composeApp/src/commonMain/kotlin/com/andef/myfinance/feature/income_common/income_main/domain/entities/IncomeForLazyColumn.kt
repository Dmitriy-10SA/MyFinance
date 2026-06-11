package com.andef.myfinance.feature.income_common.income_main.domain.entities

import com.andef.myfinance.core.domain.income_common.income.entities.IncomeModel
import kotlinx.datetime.LocalDate

data class IncomeForLazyColumn(
    val date: LocalDate,
    val totalAmount: Long,
    val incomeModels: List<IncomeModel>,
)
