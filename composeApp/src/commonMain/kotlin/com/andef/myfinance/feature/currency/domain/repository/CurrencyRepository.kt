package com.andef.myfinance.feature.currency.domain.repository

import com.andef.myfinance.feature.currency.domain.entities.CurrencyRub
import kotlinx.datetime.LocalDate

interface CurrencyRepository {
    suspend fun getAudRub(): CurrencyRub.Aud
    suspend fun getBtcRub(): CurrencyRub.Btc
    suspend fun getCadRub(): CurrencyRub.Cad
    suspend fun getChfRub(): CurrencyRub.Chf
    suspend fun getCnyRub(): CurrencyRub.Cny
    suspend fun getEthRub(): CurrencyRub.Eth
    suspend fun getEurRub(): CurrencyRub.Eur
    suspend fun getGbpRub(): CurrencyRub.Gbp
    suspend fun getJpyRub(): CurrencyRub.Jpy
    suspend fun getUsdRub(): CurrencyRub.Usd
    suspend fun getHkdRub(): CurrencyRub.Hkd
    suspend fun getAudRub(date: LocalDate): CurrencyRub.Aud
    suspend fun getBtcRub(date: LocalDate): CurrencyRub.Btc
    suspend fun getCadRub(date: LocalDate): CurrencyRub.Cad
    suspend fun getChfRub(date: LocalDate): CurrencyRub.Chf
    suspend fun getCnyRub(date: LocalDate): CurrencyRub.Cny
    suspend fun getEthRub(date: LocalDate): CurrencyRub.Eth
    suspend fun getEurRub(date: LocalDate): CurrencyRub.Eur
    suspend fun getGbpRub(date: LocalDate): CurrencyRub.Gbp
    suspend fun getJpyRub(date: LocalDate): CurrencyRub.Jpy
    suspend fun getUsdRub(date: LocalDate): CurrencyRub.Usd
    suspend fun getHkdRub(date: LocalDate): CurrencyRub.Hkd
}