package com.andef.myfinance

import android.graphics.Typeface
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.andef.myfinance.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initClickListenerForTextViews()
        initClickListenersForCardViews()
    }

    private fun initClickListenerForTextViews() {
        with(binding) {
            textViewDay.setOnClickListener {
                noticeTextView(isDay = true)
            }
            textViewWeek.setOnClickListener {
                noticeTextView(isWeek = true)
            }
            textViewMonth.setOnClickListener {
                noticeTextView(isMonth = true)
            }
            textViewYear.setOnClickListener {
                noticeTextView(isYear = true)
            }
            textViewPeriod.setOnClickListener {
                noticeTextView(isPeriod = true)
            }
        }
    }

    private fun noticeTextView(
        isDay: Boolean = false,
        isWeek: Boolean = false,
        isMonth: Boolean = false,
        isYear: Boolean = false,
        isPeriod: Boolean = false
    ) {
        with(binding) {
            textViewDay.typeface = Typeface.DEFAULT
            textViewWeek.typeface = Typeface.DEFAULT
            textViewMonth.typeface = Typeface.DEFAULT
            textViewYear.typeface = Typeface.DEFAULT
            textViewPeriod.typeface = Typeface.DEFAULT
            if (isDay) textViewDay.typeface = Typeface.DEFAULT_BOLD
            else if (isWeek) textViewWeek.typeface = Typeface.DEFAULT_BOLD
            else if (isMonth) textViewMonth.typeface = Typeface.DEFAULT_BOLD
            else if (isYear) textViewYear.typeface = Typeface.DEFAULT_BOLD
            else if (isPeriod) textViewPeriod.typeface = Typeface.DEFAULT_BOLD
        }
    }

    private fun initClickListenersForCardViews() {
        with(binding) {
            cardViewIncomes.setOnClickListener {
                showIncomesFragment()
                noticeIncomesCardView()
            }
            cardViewExpenses.setOnClickListener {
                showExpensesFragment()
                noticeExpensesCardView()
            }
        }
    }

    private fun noticeIncomesCardView() {
        with(binding) {
            textViewIncomes.typeface = Typeface.DEFAULT_BOLD
            textViewExpenses.typeface = Typeface.DEFAULT
        }
    }

    private fun noticeExpensesCardView() {
        with(binding) {
            textViewIncomes.typeface = Typeface.DEFAULT
            textViewExpenses.typeface = Typeface.DEFAULT_BOLD
        }
    }

    private fun showIncomesFragment() {
//        val incomesFragment = IncomesFragment.newInstance()
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragmentContainerViewMain, incomesFragment)
//            .commit()
    }

    private fun showExpensesFragment() {
//        val expensesFragment = ExpensesFragment.newInstance()
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragmentContainerViewMain, expensesFragment)
//            .commit()
    }
}