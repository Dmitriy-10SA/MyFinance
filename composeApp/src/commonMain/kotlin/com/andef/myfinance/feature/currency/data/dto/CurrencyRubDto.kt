package com.andef.myfinance.feature.currency.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class CurrencyRubDto {
    @Serializable
    data class AudRubDto(@SerialName(AUD) val audInRubDto: CurrencyInRubDto.AudInRubDto) :
        CurrencyRubDto()

    @Serializable
    data class BtcRubDto(@SerialName(BTC) val btcInRubDto: CurrencyInRubDto.BtcInRubDto) :
        CurrencyRubDto()

    @Serializable
    data class CadRubDto(@SerialName(CAD) val cadInRubDto: CurrencyInRubDto.CadInRubDto) :
        CurrencyRubDto()

    @Serializable
    data class ChfRubDto(@SerialName(CHF) val chfInRubDto: CurrencyInRubDto.ChfInRubDto) :
        CurrencyRubDto()

    @Serializable
    data class CnyRubDto(@SerialName(CNY) val cnyInRubDto: CurrencyInRubDto.CnyInRubDto) :
        CurrencyRubDto()

    @Serializable
    data class EthRubDto(@SerialName(ETH) val ethInRubDto: CurrencyInRubDto.EthInRubDto) :
        CurrencyRubDto()

    @Serializable
    data class EurRubDto(@SerialName(EUR) val eurInRubDto: CurrencyInRubDto.EurInRubDto) :
        CurrencyRubDto()

    @Serializable
    data class GbpRubDto(@SerialName(GBP) val gbpInRubDto: CurrencyInRubDto.GbpInRubDto) :
        CurrencyRubDto()

    @Serializable
    data class HkdRubDto(@SerialName(HKD) val hkdInRubDto: CurrencyInRubDto.HkdInRubDto) :
        CurrencyRubDto()

    @Serializable
    data class JpyRubDto(@SerialName(JPY) val jpyInRubDto: CurrencyInRubDto.JpyInRubDto) :
        CurrencyRubDto()

    @Serializable
    data class UsdRubDto(@SerialName(USD) val usdInRubDto: CurrencyInRubDto.UsdInRubDto) :
        CurrencyRubDto()

    companion object {
        const val AUD = "aud"
        const val BTC = "btc"
        const val CAD = "cad"
        const val CHF = "chf"
        const val CNY = "cny"
        const val ETH = "eth"
        const val EUR = "eur"
        const val GBP = "gbp"
        const val JPY = "jpy"
        const val USD = "usd"
        const val HKD = "hkd"
    }
}