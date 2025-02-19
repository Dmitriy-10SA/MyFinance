package com.andef.myfinance.presentation.viewmodel.income

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.domain.entities.IncomeItem
import com.andef.myfinance.domain.usecases.income.AddIncomeUseCase
import com.andef.myfinance.domain.usecases.income.RemoveIncomeUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class IncomesViewModel @Inject constructor(
    private val addIncomeUseCase: AddIncomeUseCase,
    private val removeIncomeUseCase: RemoveIncomeUseCase
) : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _isSuccessAdd.value = false
    }

    private val _isSuccessAdd = MutableLiveData<Boolean>()
    val isSuccessAdd: LiveData<Boolean> = _isSuccessAdd

    fun addIncome(incomeItem: IncomeItem) {
        viewModelScope.launch(exceptionHandler) {
            addIncomeUseCase.execute(incomeItem)
            _isSuccessAdd.value = true
        }
    }

    fun removeIncome(id: Int) {
        viewModelScope.launch(exceptionHandler) {
            removeIncomeUseCase.execute(id)
            _isSuccessAdd.value = true
        }
    }

    fun changeIncome(id: Int, incomeItem: IncomeItem) {
        viewModelScope.launch(exceptionHandler) {
            removeIncomeUseCase.execute(id)
            addIncomeUseCase.execute(incomeItem)
            _isSuccessAdd.value = true
        }
    }
}