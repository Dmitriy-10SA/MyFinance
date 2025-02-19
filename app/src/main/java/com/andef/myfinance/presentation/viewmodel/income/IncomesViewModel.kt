package com.andef.myfinance.presentation.viewmodel.income

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myfinance.domain.entities.IncomeItem
import com.andef.myfinance.domain.usecases.income.AddIncomeUseCase
import com.andef.myfinance.domain.usecases.income.GetFullIncomeByDayUseCase
import com.andef.myfinance.domain.usecases.income.GetFullIncomeByPeriodUseCase
import com.andef.myfinance.domain.usecases.income.GetIncomesByDayUseCase
import com.andef.myfinance.domain.usecases.income.GetIncomesByPeriodUseCase
import com.andef.myfinance.domain.usecases.income.RemoveIncomeUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class IncomesViewModel @Inject constructor(
    private val addIncomeUseCase: AddIncomeUseCase
) : ViewModel() {
    private val addExceptionHandler = CoroutineExceptionHandler { _, _ ->
        _isSuccessAdd.value = false
    }

    private val _isSuccessAdd = MutableLiveData<Boolean>()
    val isSuccessAdd: LiveData<Boolean> = _isSuccessAdd

    fun addIncome(incomeItem: IncomeItem) {
        viewModelScope.launch(addExceptionHandler) {
            addIncomeUseCase.execute(incomeItem)
            _isSuccessAdd.value = true
        }
    }
}