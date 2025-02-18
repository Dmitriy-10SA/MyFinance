package com.andef.myfinance.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.andef.myfinance.R
import com.andef.myfinance.databinding.ActivityIncomesBinding
import com.andef.myfinance.presentation.app.MyFinanceApplication
import com.andef.myfinance.presentation.factory.ViewModelFactory
import com.andef.myfinance.presentation.viewmodel.IncomesViewModel
import javax.inject.Inject

class IncomesActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityIncomesBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[IncomesViewModel::class.java]
    }

    private val component by lazy {
        (application as MyFinanceApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incomes)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, IncomesActivity::class.java)
        }
    }
}