package com.andef.myfinance.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.andef.myfinance.databinding.ActivityExpensesBinding
import com.andef.myfinance.presentation.app.MyFinanceApplication
import com.andef.myfinance.presentation.factory.ViewModelFactory
import com.andef.myfinance.presentation.viewmodel.expense.ExpensesViewModel
import javax.inject.Inject

class ExpensesActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityExpensesBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as MyFinanceApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ExpensesViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ExpensesActivity::class.java)
        }
    }
}