package com.andef.myfinance.feature.backup.domain.entities

import com.andef.myfinance.core.domain.expense.entities.Expense
import com.andef.myfinance.core.domain.expense_category.entities.ExpenseCategory
import com.andef.myfinance.core.domain.income.entities.Income
import com.andef.myfinance.core.domain.income_category.entities.IncomeCategory
import com.andef.myfinance.core.domain.reminder.entities.Reminder

data class BackupData(
    val allIncomes: List<Income>,
    val allExpenses: List<Expense>,
    val allReminders: List<Reminder>,
    val incomeCategories: List<IncomeCategory>,
    val expenseCategories: List<ExpenseCategory>,
    val username: String
)