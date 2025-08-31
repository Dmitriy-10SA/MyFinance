package com.andef.myfinance.core.di.income_common

import com.andef.myfinance.core.data.income_common.income_category.dao.IncomeCategoryDao
import com.andef.myfinance.core.data.income_common.income_category.mapper.IncomeCategoryMapper
import com.andef.myfinance.core.data.income_common.income_category.repository.IncomeCategoryRepositoryImpl
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

val incomeCategoryModule = listOf(
    incomeCategoryRepositoryModule,
    incomeCategoryDaoModule,
    incomeCategoryMapperModule
)