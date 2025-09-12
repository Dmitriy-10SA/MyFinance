package com.andef.myfinance.core.di.expense_common

import com.andef.myfinance.core.data.expense_common.expense.dao.ExpenseDao
import com.andef.myfinance.core.data.expense_common.expense.mapper.ExpenseMapper
import com.andef.myfinance.core.data.expense_common.expense.repository.ExpenseRepositoryImpl
import com.andef.myfinance.core.domain.expense_common.expense.repository.ExpenseRepository
import com.andef.myfinance.core.domain.expense_common.expense.usecases.AddExpenseUseCase
import com.andef.myfinance.core.domain.expense_common.expense.usecases.ChangeAllExpenseCategoryByOldCategoryUseCase
import com.andef.myfinance.core.domain.expense_common.expense.usecases.DeleteAllExpensesByCategoryUseCase
import com.andef.myfinance.core.domain.expense_common.expense.usecases.DeleteExpenseUseCase
import com.andef.myfinance.core.domain.expense_common.expense.usecases.GetExpenseByIdUseCase
import com.andef.myfinance.core.domain.expense_common.expense.usecases.GetExpensesByDateRangeFlowUseCase
import com.andef.myfinance.core.domain.expense_common.expense.usecases.GetExpensesByDateRangeUseCase
import com.andef.myfinance.core.domain.expense_common.expense.usecases.UpdateExpenseUseCase
import com.andef.myfinance.db.AppDatabase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val expenseRepositoryModule = module {
    singleOf(::ExpenseRepositoryImpl).bind<ExpenseRepository>()
    factoryOf(::AddExpenseUseCase)
    factoryOf(::ChangeAllExpenseCategoryByOldCategoryUseCase)
    factoryOf(::DeleteAllExpensesByCategoryUseCase)
    factoryOf(::DeleteExpenseUseCase)
    factoryOf(::GetExpenseByIdUseCase)
    factoryOf(::GetExpensesByDateRangeUseCase)
    factoryOf(::GetExpensesByDateRangeFlowUseCase)
    factoryOf(::UpdateExpenseUseCase)
}

private val expenseDaoModule = module {
    single { get<AppDatabase>().expenseQueries }
    singleOf(::ExpenseDao)
}

private val expenseMapperModule = module {
    singleOf(::ExpenseMapper)
}

val expenseModule = listOf(expenseRepositoryModule, expenseDaoModule, expenseMapperModule)