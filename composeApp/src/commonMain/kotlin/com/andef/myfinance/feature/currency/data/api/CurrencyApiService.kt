package com.andef.myfinance.feature.currency.data.api

import com.andef.myfinance.feature.currency.data.dto.CurrencyRubDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.Url
import io.ktor.http.encodeURLPath

class CurrencyApiService(private val client: HttpClient) {

    suspend fun getAudRub(): CurrencyRubDto.AudRubDto = client.get(url(AUD)).body()
    suspend fun getBtcRub(): CurrencyRubDto.BtcRubDto = client.get(url(BTC)).body()
    suspend fun getCadRub(): CurrencyRubDto.CadRubDto = client.get(url(CAD)).body()
    suspend fun getChfRub(): CurrencyRubDto.ChfRubDto = client.get(url(CHF)).body()
    suspend fun getCnyRub(): CurrencyRubDto.CnyRubDto = client.get(url(CNY)).body()
    suspend fun getEthRub(): CurrencyRubDto.EthRubDto = client.get(url(ETH)).body()
    suspend fun getEurRub(): CurrencyRubDto.EurRubDto = client.get(url(EUR)).body()
    suspend fun getGbpRub(): CurrencyRubDto.GbpRubDto = client.get(url(GBP)).body()
    suspend fun getJpyRub(): CurrencyRubDto.JpyRubDto = client.get(url(JPY)).body()
    suspend fun getUsdRub(): CurrencyRubDto.UsdRubDto = client.get(url(USD)).body()
    suspend fun getHkdRub(): CurrencyRubDto.HkdRubDto = client.get(url(HKD)).body()

    // Методы с датой
    suspend fun getAudRub(date: String): CurrencyRubDto.AudRubDto =
        client.get(url("$QUERY_START_FOR_DATE$date$QUERY_END_FOR_DATE$AUD_SHORT_NAME_FOR_DATE")).body()

    suspend fun getBtcRub(date: String): CurrencyRubDto.BtcRubDto =
        client.get(url("$QUERY_START_FOR_DATE$date$QUERY_END_FOR_DATE$BTC_SHORT_NAME_FOR_DATE")).body()

    suspend fun getCadRub(date: String): CurrencyRubDto.CadRubDto =
        client.get(url("$QUERY_START_FOR_DATE$date$QUERY_END_FOR_DATE$CAD_SHORT_NAME_FOR_DATE")).body()

    suspend fun getChfRub(date: String): CurrencyRubDto.ChfRubDto =
        client.get(url("$QUERY_START_FOR_DATE$date$QUERY_END_FOR_DATE$CHF_SHORT_NAME_FOR_DATE")).body()

    suspend fun getCnyRub(date: String): CurrencyRubDto.CnyRubDto =
        client.get(url("$QUERY_START_FOR_DATE$date$QUERY_END_FOR_DATE$CNY_SHORT_NAME_FOR_DATE")).body()

    suspend fun getEthRub(date: String): CurrencyRubDto.EthRubDto =
        client.get(url("$QUERY_START_FOR_DATE$date$QUERY_END_FOR_DATE$ETH_SHORT_NAME_FOR_DATE")).body()

    suspend fun getEurRub(date: String): CurrencyRubDto.EurRubDto =
        client.get(url("$QUERY_START_FOR_DATE$date$QUERY_END_FOR_DATE$EUR_SHORT_NAME_FOR_DATE")).body()

    suspend fun getGbpRub(date: String): CurrencyRubDto.GbpRubDto =
        client.get(url("$QUERY_START_FOR_DATE$date$QUERY_END_FOR_DATE$GBP_SHORT_NAME_FOR_DATE")).body()

    suspend fun getJpyRub(date: String): CurrencyRubDto.JpyRubDto =
        client.get(url("$QUERY_START_FOR_DATE$date$QUERY_END_FOR_DATE$JPY_SHORT_NAME_FOR_DATE")).body()

    suspend fun getUsdRub(date: String): CurrencyRubDto.UsdRubDto =
        client.get(url("$QUERY_START_FOR_DATE$date$QUERY_END_FOR_DATE$USD_SHORT_NAME_FOR_DATE")).body()

    suspend fun getHkdRub(date: String): CurrencyRubDto.HkdRubDto =
        client.get(url("$QUERY_START_FOR_DATE$date$QUERY_END_FOR_DATE$HKD_SHORT_NAME_FOR_DATE")).body()


    private fun url(path: String): Url {
        val encodedPath = path.split("/").joinToString("/") { it.encodeURLPath() }
        return Url("https://cdn.jsdelivr.net/npm/@fawazahmed0/$encodedPath")
    }

    companion object {
        private const val AUD = "currency-api@latest/v1/currencies/aud.json"
        private const val BTC = "currency-api@latest/v1/currencies/btc.json"
        private const val CAD = "currency-api@latest/v1/currencies/cad.json"
        private const val CHF = "currency-api@latest/v1/currencies/chf.json"
        private const val CNY = "currency-api@latest/v1/currencies/cny.json"
        private const val ETH = "currency-api@latest/v1/currencies/eth.json"
        private const val EUR = "currency-api@latest/v1/currencies/eur.json"
        private const val GBP = "currency-api@latest/v1/currencies/gbp.json"
        private const val JPY = "currency-api@latest/v1/currencies/jpy.json"
        private const val USD = "currency-api@latest/v1/currencies/usd.json"
        private const val HKD = "currency-api@latest/v1/currencies/hkd.json"

        private const val QUERY_START_FOR_DATE = "currency-api@"
        private const val QUERY_END_FOR_DATE = "/v1/currencies/"

        private const val AUD_SHORT_NAME_FOR_DATE = "aud.json"
        private const val BTC_SHORT_NAME_FOR_DATE = "btc.json"
        private const val CAD_SHORT_NAME_FOR_DATE = "cad.json"
        private const val CHF_SHORT_NAME_FOR_DATE = "chf.json"
        private const val CNY_SHORT_NAME_FOR_DATE = "cny.json"
        private const val ETH_SHORT_NAME_FOR_DATE = "eth.json"
        private const val EUR_SHORT_NAME_FOR_DATE = "eur.json"
        private const val GBP_SHORT_NAME_FOR_DATE = "gbp.json"
        private const val JPY_SHORT_NAME_FOR_DATE = "jpy.json"
        private const val USD_SHORT_NAME_FOR_DATE = "usd.json"
        private const val HKD_SHORT_NAME_FOR_DATE = "hkd.json"
    }
}