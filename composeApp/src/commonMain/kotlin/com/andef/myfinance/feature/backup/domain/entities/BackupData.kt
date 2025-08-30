package com.andef.myfinance.feature.backup.domain.entities

import com.andef.myfinance.core.domain.expense.entities.ExpenseModel
import com.andef.myfinance.core.domain.expense_category.entities.ExpenseCategoryModel
import com.andef.myfinance.core.domain.income.entities.IncomeModel
import com.andef.myfinance.core.domain.income_category.entities.IncomeCategoryModel
import com.andef.myfinance.core.domain.reminder.entities.ReminderModel

data class BackupData(
    val allIncomeModels: List<IncomeModel>,
    val allExpenseModels: List<ExpenseModel>,
    val allReminderModels: List<ReminderModel>,
    val incomeCategories: List<IncomeCategoryModel>,
    val expenseCategories: List<ExpenseCategoryModel>,
    val username: String
)