package com.andef.myfinance.feature.expense.domain.usecases

import com.andef.myfinance.core.domain.expense.repository.ExpenseRepository
import com.andef.myfinance.core.domain.expense_category.entities.ExpenseCategoryModel
import kotlinx.datetime.LocalDate

class UpdateExpenseUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(
        id: Long,
        amount: Double,
        category: ExpenseCategoryModel,
        date: LocalDate,
        note: String?
    ) = repository.updateExpense(id, amount, category, date, note)
}