package com.andef.myfinance.core.domain.expense_common.expense.usecases

import com.andef.myfinance.core.domain.expense_common.expense.repository.ExpenseRepository
import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel
import kotlinx.datetime.LocalDate

class UpdateExpenseUseCase(private val repository: ExpenseRepository) {
    suspend operator fun invoke(
        id: Long,
        amount: Long,
        category: ExpenseCategoryModel,
        date: LocalDate,
        note: String?
    ) = repository.updateExpense(id, amount, category, date, note)
}
