package com.andef.myfinance.core.domain.backup.entities

import com.andef.myfinance.core.domain.expense_common.expense.entities.ExpenseModel
import com.andef.myfinance.core.domain.expense_common.expense_category.entities.ExpenseCategoryModel
import com.andef.myfinance.core.domain.income_common.income.entities.IncomeModel
import com.andef.myfinance.core.domain.income_common.income_category.entities.IncomeCategoryModel
import com.andef.myfinance.core.domain.reminder.entities.ReminderModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BackupData(
    @SerialName("allIncomes")
    val allIncomeModels: List<IncomeModel>,
    @SerialName("allExpenses")
    val allExpenseModels: List<ExpenseModel>,
    @SerialName("allReminders")
    val allReminderModels: List<ReminderModel>,
    @SerialName("incomeCategories")
    val incomeCategories: List<IncomeCategoryModel>,
    @SerialName("expenseCategories")
    val expenseCategories: List<ExpenseCategoryModel>,
    @SerialName("username")
    val username: String
)