package com.andef.myfinance.presentation.activity

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
import com.google.android.material.tabs.TabLayout
import java.util.Calendar

class MainActivity : AppCompatActivity(), OnSelectDateListener {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var screenMode = INITIAL_SCREEN_MODE
    private var financeMode = INITIAL_FINANCE_MODE

    private var startDate: Date? = null
    private var endDate: Date? = null

    private var lastSelectedId = R.id.incomes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initTabLayout()
        initNavView()
        showFragment()
    }

    private fun initTabLayout() {
        with(binding) {
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.view?.animate()?.scaleX(1.2f)?.scaleY(1.2f)
                        ?.setDuration(500)?.start()
                    when (tab?.position) {
                        0 -> noticeTabLayout(isDay = true)
                        1 -> noticeTabLayout(isWeek = true)
                        2 -> noticeTabLayout(isMonth = true)
                        3 -> noticeTabLayout(isYear = true)
                        4 -> openDatePicker()
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    tab?.view?.animate()?.scaleX(1f)?.scaleY(1f)
                        ?.setDuration(500)?.start()
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        4 -> openDatePicker()
                    }
                }
            })
            tabLayout.addTab(tabLayout.newTab().setText(R.string.day))
            tabLayout.addTab(tabLayout.newTab().setText(R.string.week))
            tabLayout.addTab(tabLayout.newTab().setText(R.string.month))
            tabLayout.addTab(tabLayout.newTab().setText(R.string.year))
            tabLayout.addTab(tabLayout.newTab().setText(R.string.period))
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
        noticeTabLayout(isPeriod = true)
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

    private fun noticeTabLayout(
        isDay: Boolean = false,
        isWeek: Boolean = false,
        isMonth: Boolean = false,
        isYear: Boolean = false,
        isPeriod: Boolean = false
    ) {
        with(binding) {
            if (isDay) {
                screenMode = DAY_MODE
                showFragment()
            } else if (isWeek) {
                screenMode = WEEK_MODE
                showFragment()
            } else if (isMonth) {
                screenMode = MONTH_MODE
                showFragment()
            } else if (isYear) {
                screenMode = YEAR_MODE
                showFragment()
            } else if (isPeriod) {
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

    private fun initNavView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.incomes -> {
                    if (lastSelectedId != R.id.incomes) {
                        lastSelectedId = R.id.incomes
                        financeMode = INCOME_MODE
                        showIncomesFragment()
                        true
                    } else {
                        false
                    }
                }

                R.id.expenses -> {
                    if (lastSelectedId != R.id.expenses) {
                        lastSelectedId = R.id.expenses
                        financeMode = EXPENSES_MODE
                        showExpensesFragment()
                        true
                    } else {
                        false
                    }
                }

                R.id.finance -> {
                    if (lastSelectedId != R.id.finance) {
                        lastSelectedId = R.id.finance
                        financeMode = FINANCE_FIN_MODE
                        showFinanceFragment()
                        true
                    } else {
                        false
                    }
                }

                else -> {
                    false
                }
            }
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
            .setCustomAnimations(
                R.anim.slide_in_up,
                R.anim.slide_out_down,
                R.anim.fade_in,
                R.anim.fade_out
            )
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
            .setCustomAnimations(
                R.anim.slide_in_up,
                R.anim.slide_out_down,
                R.anim.fade_in,
                R.anim.fade_out
            )
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
            .setCustomAnimations(
                R.anim.fade_in_long,
                R.anim.fade_out_long
            )
            .replace(R.id.fragmentContainerViewMain, financeFragment)
            .commit()
    }

    companion object {
        private const val DAY_MODE = "day"
        private const val WEEK_MODE = "week"
        private const val MONTH_MODE = "month"
        private const val YEAR_MODE = "year"
        private const val PERIOD_MODE = "period"
        private const val INITIAL_SCREEN_MODE = DAY_MODE

        private const val INCOME_MODE = "income"
        private const val EXPENSES_MODE = "expenses"
        private const val FINANCE_FIN_MODE = "financeFinMode"
        private const val INITIAL_FINANCE_MODE = INCOME_MODE
    }
}