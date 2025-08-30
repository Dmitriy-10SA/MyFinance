package com.andef.myfinance.feature.currency.data.repository

import com.andef.myfinance.feature.currency.data.api.CurrencyApiService
import com.andef.myfinance.feature.currency.data.mapper.CurrencyMapper
import com.andef.myfinance.feature.currency.domain.entities.CurrencyRub
import com.andef.myfinance.feature.currency.domain.repository.CurrencyRepository
import kotlinx.datetime.LocalDate

class CurrencyRepositoryImpl(
    private val api: CurrencyApiService,
    private val mapper: CurrencyMapper
) : CurrencyRepository {
    override suspend fun getAudRub(): CurrencyRub.Aud {
        val dto = api.getAudRub()
        return mapper.map(dto) as CurrencyRub.Aud
    }

    override suspend fun getAudRub(date: LocalDate): CurrencyRub.Aud {
        val dto = api.getAudRub(date.toString())
        return mapper.map(dto) as CurrencyRub.Aud
    }

    override suspend fun getBtcRub(): CurrencyRub.Btc {
        val dto = api.getBtcRub()
        return mapper.map(dto) as CurrencyRub.Btc
    }

    override suspend fun getBtcRub(date: LocalDate): CurrencyRub.Btc {
        val dto = api.getBtcRub(date.toString())
        return mapper.map(dto) as CurrencyRub.Btc
    }

    override suspend fun getCadRub(): CurrencyRub.Cad {
        val dto = api.getCadRub()
        return mapper.map(dto) as CurrencyRub.Cad
    }

    override suspend fun getCadRub(date: LocalDate): CurrencyRub.Cad {
        val dto = api.getCadRub(date.toString())
        return mapper.map(dto) as CurrencyRub.Cad
    }

    override suspend fun getChfRub(): CurrencyRub.Chf {
        val dto = api.getChfRub()
        return mapper.map(dto) as CurrencyRub.Chf
    }

    override suspend fun getChfRub(date: LocalDate): CurrencyRub.Chf {
        val dto = api.getChfRub(date.toString())
        return mapper.map(dto) as CurrencyRub.Chf
    }

    override suspend fun getCnyRub(): CurrencyRub.Cny {
        val dto = api.getCnyRub()
        return mapper.map(dto) as CurrencyRub.Cny
    }

    override suspend fun getCnyRub(date: LocalDate): CurrencyRub.Cny {
        val dto = api.getCnyRub(date.toString())
        return mapper.map(dto) as CurrencyRub.Cny
    }

    override suspend fun getEthRub(): CurrencyRub.Eth {
        val dto = api.getEthRub()
        return mapper.map(dto) as CurrencyRub.Eth
    }

    override suspend fun getEthRub(date: LocalDate): CurrencyRub.Eth {
        val dto = api.getEthRub(date.toString())
        return mapper.map(dto) as CurrencyRub.Eth
    }

    override suspend fun getEurRub(): CurrencyRub.Eur {
        val dto = api.getEurRub()
        return mapper.map(dto) as CurrencyRub.Eur
    }

    override suspend fun getEurRub(date: LocalDate): CurrencyRub.Eur {
        val dto = api.getEurRub(date.toString())
        return mapper.map(dto) as CurrencyRub.Eur
    }

    override suspend fun getGbpRub(): CurrencyRub.Gbp {
        val dto = api.getGbpRub()
        return mapper.map(dto) as CurrencyRub.Gbp
    }

    override suspend fun getGbpRub(date: LocalDate): CurrencyRub.Gbp {
        val dto = api.getGbpRub(date.toString())
        return mapper.map(dto) as CurrencyRub.Gbp
    }

    override suspend fun getJpyRub(): CurrencyRub.Jpy {
        val dto = api.getJpyRub()
        return mapper.map(dto) as CurrencyRub.Jpy
    }

    override suspend fun getJpyRub(date: LocalDate): CurrencyRub.Jpy {
        val dto = api.getJpyRub(date.toString())
        return mapper.map(dto) as CurrencyRub.Jpy
    }

    override suspend fun getUsdRub(): CurrencyRub.Usd {
        val dto = api.getUsdRub()
        return mapper.map(dto) as CurrencyRub.Usd
    }

    override suspend fun getUsdRub(date: LocalDate): CurrencyRub.Usd {
        val dto = api.getUsdRub(date.toString())
        return mapper.map(dto) as CurrencyRub.Usd
    }

    override suspend fun getHkdRub(): CurrencyRub.Hkd {
        val dto = api.getHkdRub()
        return mapper.map(dto) as CurrencyRub.Hkd
    }

    override suspend fun getHkdRub(date: LocalDate): CurrencyRub.Hkd {
        val dto = api.getHkdRub(date.toString())
        return mapper.map(dto) as CurrencyRub.Hkd
    }
}