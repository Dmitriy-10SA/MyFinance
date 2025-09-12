package com.andef.myfinance.core.di.expense_common

import com.andef.myfinance.core.data.expense_common.expense_category.dao.ExpenseCategoryDao
import com.andef.myfinance.core.data.expense_common.expense_category.mapper.ExpenseCategoryMapper
import com.andef.myfinance.core.data.expense_common.expense_category.repository.ExpenseCategoryRepositoryImpl
import com.andef.myfinance.core.domain.expense_common.expense_category.repository.ExpenseCategoryRepository
import com.andef.myfinance.core.domain.expense_common.expense_category.usecases.AddExpenseCategoryUseCase
import com.andef.myfinance.core.domain.expense_common.expense_category.usecases.ChangeExpenseCategoryUseCase
import com.andef.myfinance.core.domain.expense_common.expense_category.usecases.DeleteExpenseCategoryUseCase
import com.andef.myfinance.core.domain.expense_common.expense_category.usecases.GetExpenseCategoriesAsFlowUseCase
import com.andef.myfinance.core.domain.expense_common.expense_category.usecases.GetExpenseCategoriesUseCase
import com.andef.myfinance.db.AppDatabase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val expenseCategoryRepositoryModule = module {
    singleOf(::ExpenseCategoryRepositoryImpl).bind<ExpenseCategoryRepository>()
    factoryOf(::AddExpenseCategoryUseCase)
    factoryOf(::ChangeExpenseCategoryUseCase)
    factoryOf(::DeleteExpenseCategoryUseCase)
    factoryOf(::GetExpenseCategoriesAsFlowUseCase)
    factoryOf(::GetExpenseCategoriesUseCase)
}

private val expenseCategoryDaoModule = module {
    single { get<AppDatabase>().expenseCategoryQueries }
    singleOf(::ExpenseCategoryDao)
}

private val expenseCategoryMapperModule = module {
    singleOf(::ExpenseCategoryMapper)
}

val expenseCategoryModule = listOf(
    expenseCategoryRepositoryModule,
    expenseCategoryDaoModule,
    expenseCategoryMapperModule
)