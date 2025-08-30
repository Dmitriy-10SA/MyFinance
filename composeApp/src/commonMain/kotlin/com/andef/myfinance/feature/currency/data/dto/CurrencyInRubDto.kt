package com.andef.myfinance.feature.currency.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class CurrencyInRubDto {
    @Serializable
    data class AudInRubDto(@SerialName(RUB) val amount: Double) : CurrencyInRubDto()

    @Serializable
    data class BtcInRubDto(@SerialName(RUB) val amount: Double) : CurrencyInRubDto()

    @Serializable
    data class CadInRubDto(@SerialName(RUB) val amount: Double) : CurrencyInRubDto()

    @Serializable
    data class ChfInRubDto(@SerialName(RUB) val amount: Double) : CurrencyInRubDto()

    @Serializable
    data class CnyInRubDto(@SerialName(RUB) val amount: Double) : CurrencyInRubDto()

    @Serializable
    data class EthInRubDto(@SerialName(RUB) val amount: Double) : CurrencyInRubDto()

    @Serializable
    data class EurInRubDto(@SerialName(RUB) val amount: Double) : CurrencyInRubDto()

    @Serializable
    data class GbpInRubDto(@SerialName(RUB) val amount: Double) : CurrencyInRubDto()

    @Serializable
    data class HkdInRubDto(@SerialName(RUB) val amount: Double) : CurrencyInRubDto()

    @Serializable
    data class JpyInRubDto(@SerialName(RUB) val amount: Double) : CurrencyInRubDto()

    @Serializable
    data class UsdInRubDto(@SerialName(RUB) val amount: Double) : CurrencyInRubDto()

    companion object {
        const val RUB = "rub"
    }
}