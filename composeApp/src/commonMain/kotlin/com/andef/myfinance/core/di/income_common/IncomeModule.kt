package com.andef.myfinance.core.di.income_common

import com.andef.myfinance.core.data.income_common.income.dao.IncomeDao
import com.andef.myfinance.core.data.income_common.income.mapper.IncomeMapper
import com.andef.myfinance.core.data.income_common.income.repository.IncomeRepositoryImpl
import com.andef.myfinance.core.domain.income_common.income.repository.IncomeRepository
import com.andef.myfinance.core.domain.income_common.income.usecases.AddIncomeUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.ChangeAllIncomeCategoryByOldCategoryUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.DeleteAllIncomesByCategoryUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.DeleteIncomeUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.GetIncomeByIdUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.GetIncomesByDateRangeFlowUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.GetIncomesByDateRangeUseCase
import com.andef.myfinance.core.domain.income_common.income.usecases.UpdateIncomeUseCase
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

val incomeModule = listOf(incomeRepositoryModule, incomeDaoModule, incomeMapperModule)