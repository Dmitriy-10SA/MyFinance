package com.andef.myfinance.feature.currency.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.feature.currency.domain.entities.CurrencyRub
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate

class CurrencysViewModel(
    private val getAudRubUseCase: GetAudRubUseCase,
    private val getBtcRubUseCase: GetBtcRubUseCase,
    private val getCadRubUseCase: GetCadRubUseCase,
    private val getChfRubUseCase: GetChfRubUseCase,
    private val getCnyRubUseCase: GetCnyRubUseCase,
    private val getEthRubUseCase: GetEthRubUseCase,
    private val getEurRubUseCase: GetEurRubUseCase,
    private val getGbpRubUseCase: GetGbpRubUseCase,
    private val getJpyRubUseCase: GetJpyRubUseCase,
    private val getUsdRubUseCase: GetUsdRubUseCase,
    private val getHkdRubUseCase: GetHkdRubUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CurrencysState())
    val state: StateFlow<CurrencysState> = _state

    fun send(intent: CurrencysIntent) {
        when (intent) {
            is CurrencysIntent.LoadCurrencys -> {
                loadCurrencys(intent.date)
            }
        }
    }

    private fun calcPercent(now: Double, before: Double) =
        (((now - before) / before) * 100).toFloat()

    private var job: Job? = null
    private fun loadCurrencys(date: LocalDate) {
        job?.cancel()
        job = viewModelScope.launch {
            try {
                _state.value =
                    _state.value.copy(isLoading = true, currencys = emptyList(), isError = false)
                val allCurrency = mutableListOf<Pair<CurrencyRub, Float>>()
                val bestCurrency = loadFirstCurrencys(date)
                allCurrency.addAll(bestCurrency)
                val secondBestCurrency = loadSecondCurrencys(date)
                allCurrency.addAll(secondBestCurrency)
                val thirdBestCurrency = loadThirdCurrencys(date)
                allCurrency.addAll(thirdBestCurrency)
                val otherCurrency = loadOtherCurrency(date)
                allCurrency.addAll(otherCurrency)
                _state.value = _state.value.copy(currencys = allCurrency)
            } catch (_: Exception) {
                _state.value = _state.value.copy(isError = true)
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    private suspend fun loadFirstCurrencys(date: LocalDate): List<Pair<CurrencyRub, Float>> {
        return withContext(Dispatchers.IO) {
            val usdRubNow = async { getUsdRubUseCase.invoke() }
            val eurRubNow = async { getEurRubUseCase.invoke() }
            val cnyRubNow = async { getCnyRubUseCase.invoke() }
            val usdRubBefore = async { getUsdRubUseCase.invoke(date) }
            val eurRubBefore = async { getEurRubUseCase.invoke(date) }
            val cnyRubBefore = async { getCnyRubUseCase.invoke(date) }

            val usdNow = usdRubNow.await()
            val eurNow = eurRubNow.await()
            val cnyNow = cnyRubNow.await()
            val usdBefore = usdRubBefore.await()
            val eurBefore = eurRubBefore.await()
            val cnyBefore = cnyRubBefore.await()

            mutableListOf<Pair<CurrencyRub, Float>>().apply {
                add(usdNow to calcPercent(usdNow.amount, usdBefore.amount))
                add(eurNow to calcPercent(eurNow.amount, eurBefore.amount))
                add(cnyNow to calcPercent(cnyNow.amount, cnyBefore.amount))
            }.toList()
        }
    }

    private suspend fun loadSecondCurrencys(date: LocalDate): List<Pair<CurrencyRub, Float>> {
        return withContext(Dispatchers.IO) {
            val jpyRubNow = async { getJpyRubUseCase.invoke() }
            val gbpRubNow = async { getGbpRubUseCase.invoke() }
            val btcRubNow = async { getBtcRubUseCase.invoke() }
            val jpyRubBefore = async { getJpyRubUseCase.invoke(date) }
            val gbpRubBefore = async { getGbpRubUseCase.invoke(date) }
            val btcRubBefore = async { getBtcRubUseCase.invoke(date) }

            val jpyNow = jpyRubNow.await()
            val gbpNow = gbpRubNow.await()
            val btcNow = btcRubNow.await()
            val jpyBefore = jpyRubBefore.await()
            val gbpBefore = gbpRubBefore.await()
            val btcBefore = btcRubBefore.await()

            mutableListOf<Pair<CurrencyRub, Float>>().apply {
                add(jpyNow to calcPercent(jpyNow.amount, jpyBefore.amount))
                add(gbpNow to calcPercent(gbpNow.amount, gbpBefore.amount))
                add(btcNow to calcPercent(btcNow.amount, btcBefore.amount))
            }.toList()
        }
    }

    private suspend fun loadThirdCurrencys(date: LocalDate): List<Pair<CurrencyRub, Float>> {
        return withContext(Dispatchers.IO) {
            val ethRubNow = async { getEthRubUseCase.invoke() }
            val chfRubNow = async { getChfRubUseCase.invoke() }
            val audRubNow = async { getAudRubUseCase.invoke() }
            val ethRubBefore = async { getEthRubUseCase.invoke(date) }
            val chfRubBefore = async { getChfRubUseCase.invoke(date) }
            val audRubBefore = async { getAudRubUseCase.invoke(date) }

            val ethNow = ethRubNow.await()
            val chfNow = chfRubNow.await()
            val audNow = audRubNow.await()
            val ethBefore = ethRubBefore.await()
            val chfBefore = chfRubBefore.await()
            val audBefore = audRubBefore.await()

            mutableListOf<Pair<CurrencyRub, Float>>().apply {
                add(ethNow to calcPercent(ethNow.amount, ethBefore.amount))
                add(chfNow to calcPercent(chfNow.amount, chfBefore.amount))
                add(audNow to calcPercent(audNow.amount, audBefore.amount))
            }.toList()
        }
    }

    private suspend fun loadOtherCurrency(date: LocalDate): List<Pair<CurrencyRub, Float>> {
        return withContext(Dispatchers.IO) {
            val cadRubNow = async { getCadRubUseCase.invoke() }
            val hkdRubNow = async { getHkdRubUseCase.invoke() }
            val cadRubBefore = async { getCadRubUseCase.invoke(date) }
            val hkdRubBefore = async { getHkdRubUseCase.invoke(date) }

            val cadNow = cadRubNow.await()
            val hkdNow = hkdRubNow.await()
            val cadBefore = cadRubBefore.await()
            val hkdBefore = hkdRubBefore.await()

            mutableListOf<Pair<CurrencyRub, Float>>().apply {
                add(cadNow to calcPercent(cadNow.amount, cadBefore.amount))
                add(hkdNow to calcPercent(hkdNow.amount, hkdBefore.amount))
            }.toList()
        }
    }
}