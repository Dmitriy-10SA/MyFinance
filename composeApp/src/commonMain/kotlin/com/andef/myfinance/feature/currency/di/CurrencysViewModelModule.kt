package com.andef.myfinance.feature.currency.di

import com.andef.myfinance.feature.currency.data.api.CurrencyApiService
import com.andef.myfinance.feature.currency.data.mapper.CurrencyMapper
import com.andef.myfinance.feature.currency.data.repository.CurrencyRepositoryImpl
import com.andef.myfinance.feature.currency.domain.repository.CurrencyRepository
import com.andef.myfinance.feature.currency.domain.usecases.GetAudRubUseCase
import com.andef.myfinance.feature.currency.domain.usecases.GetBtcRubUseCase
import com.andef.myfinance.feature.currency.domain.usecases.GetCadRubUseCase
import com.andef.myfinance.feature.currency.domain.usecases.GetChfRubUseCase
import com.andef.myfinance.feature.currency.domain.usecases.GetCnyRubUseCase
import com.andef.myfinance.feature.currency.domain.usecases.GetEthRubUseCase
import com.andef.myfinance.feature.currency.domain.usecases.GetEurRubUseCase
import com.andef.myfinance.feature.currency.domain.usecases.GetGbpRubUseCase
import com.andef.myfinance.feature.currency.domain.usecases.GetHkdRubUseCase
import com.andef.myfinance.feature.currency.domain.usecases.GetJpyRubUseCase
import com.andef.myfinance.feature.currency.domain.usecases.GetUsdRubUseCase
import com.andef.myfinance.feature.currency.presentation.CurrencysViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val currencysRepositoryModule = module {
    singleOf(::CurrencyRepositoryImpl).bind<CurrencyRepository>()
    factoryOf(::GetAudRubUseCase)
    factoryOf(::GetBtcRubUseCase)
    factoryOf(::GetCadRubUseCase)
    factoryOf(::GetChfRubUseCase)
    factoryOf(::GetCnyRubUseCase)
    factoryOf(::GetEthRubUseCase)
    factoryOf(::GetEurRubUseCase)
    factoryOf(::GetGbpRubUseCase)
    factoryOf(::GetHkdRubUseCase)
    factoryOf(::GetJpyRubUseCase)
    factoryOf(::GetUsdRubUseCase)
}

private val currencysMapper = module {
    singleOf(::CurrencyMapper)
}

private val currencysApiServiceModule = module {
    singleOf(::CurrencyApiService)
}

private val currencysViewModelModule = module {
    viewModelOf(::CurrencysViewModel)
}

val currencysModule = listOf(
    currencysRepositoryModule,
    currencysMapper,
    currencysApiServiceModule,
    currencysViewModelModule
)