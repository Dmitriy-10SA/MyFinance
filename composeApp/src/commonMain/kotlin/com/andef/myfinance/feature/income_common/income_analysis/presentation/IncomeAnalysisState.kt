package com.andef.myfinance.feature.income_common.income_analysis.presentation

import com.andef.myfinance.core.domain.income_common.income_category.entities.IncomeCategoryModel

data class IncomeAnalysisState(
    val incomesForAnalysis: List<Pair<IncomeCategoryModel, Double>> = emptyList(),
    val totalAmount: Double = 0.0,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)