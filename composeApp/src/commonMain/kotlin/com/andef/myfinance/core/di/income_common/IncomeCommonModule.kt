package com.andef.myfinance.core.di.income_common

import com.andef.myfinance.core.data.income_common.income.dao.IncomeDao
import com.andef.myfinance.core.data.income_common.income.mapper.IncomeMapper
import com.andef.myfinance.core.data.income_common.income.repository.IncomeRepositoryImpl
import com.andef.myfinance.core.data.income_common.income_category.dao.IncomeCategoryDao
import com.andef.myfinance.core.data.income_common.income_category.mapper.IncomeCategoryMapper
import com.andef.myfinance.core.data.income_common.income_category.repository.IncomeCategoryRepositoryImpl
import com.andef.myfinance.core.domain.income_common.income.repository.IncomeRepository
import com.andef.myfinance.core.domain.income_common.income.usecases.AddIncomeUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.ChangeAllIncomeCategoryByOldCategoryUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.DeleteAllIncomesByCategoryUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.DeleteIncomeUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.GetIncomeByIdUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.GetIncomesByDateRangeFlowUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.GetIncomesByDateRangeUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.UpdateIncomeUseCase
import com.andef.myfinance.core.domain.income_common.income_category.repository.IncomeCategoryRepository
import com.andef.myfinance.core.domain.income_common.income_category.usecases.AddIncomeCategoryUseCase
import com.andef.myfinance.core.domain.income_common.income_category.usecases.ChangeIncomeCategoryUseCase
import com.andef.myfinance.core.domain.income_common.income_category.usecases.DeleteIncomeCategoryUseCase
import com.andef.myfinance.core.domain.income_common.income_category.usecases.GetIncomeCategoriesAsFlowUseCase
import com.andef.myfinance.core.domain.income_common.income_category.usecases.GetIncomeCategoriesUseCase
import com.andef.myfinance.db.AppDatabase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val incomeRepositoryModule = module {
    singleOf(::IncomeRepositoryImpl).bind<IncomeRepository>()
    factoryOf(::AddIncomeUseCase)
    factoryOf(::ChangeAllIncomeCategoryByOldCategoryUseCase)
    factoryOf(::DeleteAllIncomesByCategoryUseCase)
    factoryOf(::DeleteIncomeUseCase)
    factoryOf(::GetIncomeByIdUseCase)
    factoryOf(::GetIncomesByDateRangeUseCase)
    factoryOf(::GetIncomesByDateRangeFlowUseCase)
    factoryOf(::UpdateIncomeUseCase)
}

private val incomeDaoModule = module {
    single { get<AppDatabase>().incomeQueries }
    singleOf(::IncomeDao)
}

private val incomeMapperModule = module {
    singleOf(::IncomeMapper)
}

private val incomeCategoryRepositoryModule = module {
    singleOf(::IncomeCategoryRepositoryImpl).bind<IncomeCategoryRepository>()
    factoryOf(::AddIncomeCategoryUseCase)
    factoryOf(::ChangeIncomeCategoryUseCase)
    factoryOf(::DeleteIncomeCategoryUseCase)
    factoryOf(::GetIncomeCategoriesAsFlowUseCase)
    factoryOf(::GetIncomeCategoriesUseCase)
}

private val incomeCategoryDaoModule = module {
    single { get<AppDatabase>().incomeCategoryQueries }
    singleOf(::IncomeCategoryDao)
}

private val incomeCategoryMapperModule = module {
    singleOf(::IncomeCategoryMapper)
}

val incomeCommonModule = listOf(
    incomeRepositoryModule,
    incomeCategoryRepositoryModule,
    incomeDaoModule,
    incomeMapperModule,
    incomeCategoryDaoModule,
    incomeCategoryMapperModule
)