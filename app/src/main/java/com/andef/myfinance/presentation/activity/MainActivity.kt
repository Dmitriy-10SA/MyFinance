package com.andef.myfinance.presentation.activity

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andef.myfinance.R
import com.andef.myfinance.databinding.ActivityMainBinding
import com.andef.myfinance.domain.entities.Date
import com.andef.myfinance.presentation.fragment.ExpensesFragment
import com.andef.myfinance.presentation.fragment.FinanceFragment
import com.andef.myfinance.presentation.fragment.IncomesFragment
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import java.util.Calendar

class MainActivity : AppCompatActivity(), OnSelectDateListener {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var screenMode = INITIAL_SCREEN_MODE
    private var financeMode = INITIAL_FINANCE_MODE

    private var startDate: Date? = null
    private var endDate: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initClickListenerForTextViews()
        initClickListenersForCardViews()
        loadData(savedInstanceState)
    }

    private fun loadData(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            showIncomesFragment()
        } else {
            screenMode = savedInstanceState.getString(SCREEN_MODE)
                ?: throw RuntimeException("Unknown screenMode: $this.")
            financeMode = savedInstanceState.getString(FINANCE_MODE)
                ?: throw RuntimeException("Unknown financeMode: $this.")
            startDate = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                @Suppress("DEPRECATION")
                (savedInstanceState.getParcelable(START_DATE))
            } else {
                savedInstanceState.getParcelable(START_DATE, Date::class.java)
            }
            endDate = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                @Suppress("DEPRECATION")
                savedInstanceState.getParcelable(END_DATE)
            } else {
                savedInstanceState.getParcelable(END_DATE, Date::class.java)
            }
            noticeCardView()
            firstNoticeTextView()
        }
    }

    private fun firstNoticeTextView() {
        when (screenMode) {
            DAY_MODE -> noticeTextView(isDay = true)
            WEEK_MODE -> noticeTextView(isWeek = true)
            MONTH_MODE -> noticeTextView(isMonth = true)
            YEAR_MODE -> noticeTextView(isYear = true)
            PERIOD_MODE -> noticeTextView(isPeriod = true)
            else -> throw RuntimeException("Unknown screenMode: $this.")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(SCREEN_MODE, screenMode)
        outState.putString(FINANCE_MODE, financeMode)
        outState.putParcelable(START_DATE, startDate)
        outState.putParcelable(END_DATE, endDate)
        super.onSaveInstanceState(outState)
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
                openDatePicker()
            }
        }
    }

    override fun onSelect(calendar: List<Calendar>) {
        val selectedStartDate = calendar[0]
        val selectedEndDate = calendar[calendar.size - 1]
        startDate = Date(
            selectedStartDate[Calendar.DAY_OF_MONTH],
            selectedStartDate[Calendar.MONTH] + 1,
            selectedStartDate[Calendar.YEAR]
        )
        endDate = Date(
            selectedEndDate[Calendar.DAY_OF_MONTH],
            selectedEndDate[Calendar.MONTH] + 1,
            selectedEndDate[Calendar.YEAR]
        )
        noticeTextView(isPeriod = true)
    }

    private fun openDatePicker() {
        DatePickerBuilder(this, this)
            .pickerType(CalendarView.RANGE_PICKER)
            .headerColor(R.color.my_blue)
            .headerLabelColor(R.color.white)
            .abbreviationsLabelsColor(R.color.black)
            .pagesColor(R.color.white)
            .selectionColor(R.color.my_blue)
            .selectionLabelColor(R.color.white)
            .daysLabelsColor(R.color.black)
            .dialogButtonsColor(R.color.black)
            .todayLabelColor(R.color.my_blue)
            .build()
            .show()
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
            if (isDay) {
                textViewDay.typeface = Typeface.DEFAULT_BOLD
                screenMode = DAY_MODE
                showFragment()
            } else if (isWeek) {
                textViewWeek.typeface = Typeface.DEFAULT_BOLD
                screenMode = WEEK_MODE
                showFragment()
            } else if (isMonth) {
                textViewMonth.typeface = Typeface.DEFAULT_BOLD
                screenMode = MONTH_MODE
                showFragment()
            } else if (isYear) {
                textViewYear.typeface = Typeface.DEFAULT_BOLD
                screenMode = YEAR_MODE
                showFragment()
            } else if (isPeriod) {
                textViewPeriod.typeface = Typeface.DEFAULT_BOLD
                screenMode = PERIOD_MODE
                showFragment()
            } else {
                throw RuntimeException("Parameter value not found: $this.")
            }
        }
    }

    private fun showFragment() {
        when (financeMode) {
            INCOME_MODE -> showIncomesFragment()
            EXPENSES_MODE -> showExpensesFragment()
            FINANCE_FIN_MODE -> showFinanceFragment()
            else -> throw RuntimeException("Unknown financeMode: $this.")
        }
    }

    private fun initClickListenersForCardViews() {
        with(binding) {
            cardViewIncomes.setOnClickListener {
                financeMode = INCOME_MODE
                noticeIncomesCardView()
                showIncomesFragment()
            }
            cardViewExpenses.setOnClickListener {
                financeMode = EXPENSES_MODE
                noticeExpensesCardView()
                showExpensesFragment()
            }
            cardViewFinance.setOnClickListener {
                financeMode = FINANCE_FIN_MODE
                noticeFinanceCardView()
                showFinanceFragment()
            }
        }
    }

    private fun noticeCardView() {
        when (financeMode) {
            INCOME_MODE -> noticeIncomesCardView()
            EXPENSES_MODE -> noticeExpensesCardView()
            FINANCE_FIN_MODE -> noticeFinanceCardView()
            else -> throw RuntimeException("Unknown screenMode: $this.")
        }
    }

    private fun noticeIncomesCardView() {
        with(binding) {
            textViewIncomes.typeface = Typeface.DEFAULT_BOLD
            textViewExpenses.typeface = Typeface.DEFAULT
            textViewFinance.typeface = Typeface.DEFAULT
        }
    }

    private fun noticeFinanceCardView() {
        with(binding) {
            textViewIncomes.typeface = Typeface.DEFAULT
            textViewExpenses.typeface = Typeface.DEFAULT
            textViewFinance.typeface = Typeface.DEFAULT_BOLD
        }
    }

    private fun noticeExpensesCardView() {
        with(binding) {
            textViewIncomes.typeface = Typeface.DEFAULT
            textViewExpenses.typeface = Typeface.DEFAULT_BOLD
            textViewFinance.typeface = Typeface.DEFAULT
        }
    }

    private fun showIncomesFragment() {
        val incomesFragment = if (screenMode == DAY_MODE) {
            IncomesFragment.newInstanceNotPeriod(isDayMode = true)
        } else if (screenMode == WEEK_MODE) {
            IncomesFragment.newInstanceNotPeriod(isWeekMode = true)
        } else if (screenMode == MONTH_MODE) {
            IncomesFragment.newInstanceNotPeriod(isMonthMode = true)
        } else if (screenMode == YEAR_MODE) {
            IncomesFragment.newInstanceNotPeriod(isYearMode = true)
        } else if (screenMode == PERIOD_MODE) {
            if (startDate != null && endDate != null) {
                IncomesFragment.newInstancePeriod(startDate!!, endDate!!)
            } else {
                throw RuntimeException("Not initial values: $this.")
            }
        } else {
            throw RuntimeException("Unknown screen mode: $this.")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerViewMain, incomesFragment)
            .commit()
    }

    private fun showExpensesFragment() {
        val expensesFragment = if (screenMode == DAY_MODE) {
            ExpensesFragment.newInstanceNotPeriod(isDayMode = true)
        } else if (screenMode == WEEK_MODE) {
            ExpensesFragment.newInstanceNotPeriod(isWeekMode = true)
        } else if (screenMode == MONTH_MODE) {
            ExpensesFragment.newInstanceNotPeriod(isMonthMode = true)
        } else if (screenMode == YEAR_MODE) {
            ExpensesFragment.newInstanceNotPeriod(isYearMode = true)
        } else if (screenMode == PERIOD_MODE) {
            if (startDate != null && endDate != null) {
                ExpensesFragment.newInstancePeriod(startDate!!, endDate!!)
            } else {
                throw RuntimeException("Not initial values: $this.")
            }
        } else {
            throw RuntimeException("Unknown screen mode: $this.")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerViewMain, expensesFragment)
            .commit()
    }

    private fun showFinanceFragment() {
        val financeFragment = if (screenMode == DAY_MODE) {
            FinanceFragment.newInstanceNotPeriod(isDayMode = true)
        } else if (screenMode == WEEK_MODE) {
            FinanceFragment.newInstanceNotPeriod(isWeekMode = true)
        } else if (screenMode == MONTH_MODE) {
            FinanceFragment.newInstanceNotPeriod(isMonthMode = true)
        } else if (screenMode == YEAR_MODE) {
            FinanceFragment.newInstanceNotPeriod(isYearMode = true)
        } else if (screenMode == PERIOD_MODE) {
            if (startDate != null && endDate != null) {
                FinanceFragment.newInstancePeriod(startDate!!, endDate!!)
            } else {
                throw RuntimeException("Not initial values: $this.")
            }
        } else {
            throw RuntimeException("Unknown screen mode: $this.")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerViewMain, financeFragment)
            .commit()
    }

    companion object {
        private const val SCREEN_MODE = "screenMode"
        private const val DAY_MODE = "day"
        private const val WEEK_MODE = "week"
        private const val MONTH_MODE = "month"
        private const val YEAR_MODE = "year"
        private const val PERIOD_MODE = "period"
        private const val INITIAL_SCREEN_MODE = DAY_MODE

        private const val FINANCE_MODE = "financeMode"
        private const val INCOME_MODE = "income"
        private const val EXPENSES_MODE = "expenses"
        private const val FINANCE_FIN_MODE = "financeFinMode"
        private const val INITIAL_FINANCE_MODE = INCOME_MODE

        private const val START_DATE = "startDate"
        private const val END_DATE = "endDate"
    }
}