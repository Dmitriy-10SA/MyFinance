package com.andef.myfinance.presentation.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andef.myfinance.databinding.FragmentExpensesBinding
import com.andef.myfinance.domain.entities.Date
import java.time.LocalDate

class ExpensesFragment : Fragment() {
    private var _binding: FragmentExpensesBinding? = null
    private val binding get() = _binding!!

    private lateinit var screenMode: String
    private lateinit var startDate: Date
    private lateinit var endDate: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getScreenModeAndDate()
    }

    private fun getScreenModeAndDate() {
        arguments?.let {
            screenMode = it.getString(SCREEN_MODE).toString()
            when (screenMode) {
                DAY_MODE -> getNotPeriodDate(isDayMode = true)
                WEEK_MODE -> getNotPeriodDate(isWeekMode = true)
                MONTH_MODE -> getNotPeriodDate(isMonthMode = true)
                YEAR_MODE -> getNotPeriodDate(isYearMode = true)
                PERIOD_MODE -> getPeriodDate()
                else -> throw RuntimeException("Unknown screen mode: $this.")
            }
        } ?: throw RuntimeException("Unknown screen mode: $this.")
    }

    private fun getPeriodDate() {
        startDate = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(START_PERIOD_DATE_DAY)
                ?: throw RuntimeException("Unknown value of startDate: $this.")
        } else {
            arguments?.getParcelable(START_PERIOD_DATE_DAY, Date::class.java)
                ?: throw RuntimeException("Unknown value of startDate: $this.")
        }
        endDate = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(END_PERIOD_DATE)
                ?: throw RuntimeException("Unknown value of startDate: $this.")
        } else {
            arguments?.getParcelable(END_PERIOD_DATE, Date::class.java)
                ?: throw RuntimeException("Unknown value of startDate: $this.")
        }
    }

    private fun getNotPeriodDate(
        isDayMode: Boolean = false,
        isWeekMode: Boolean = false,
        isMonthMode: Boolean = false,
        isYearMode: Boolean = false
    ) {
        var currentDate = LocalDate.now()
        val startDay = currentDate.dayOfMonth
        val startMonth = currentDate.monthValue
        val startYear = currentDate.year
        startDate = Date(startDay, startMonth, startYear)
        if (isDayMode) {
            endDate = startDate
            return
        } else if (isWeekMode) {
            currentDate = currentDate.plusWeeks(1)
        } else if (isMonthMode) {
            currentDate = currentDate.plusMonths(1)
        } else if (isYearMode) {
            currentDate = currentDate.plusYears(1)
        } else {
            throw RuntimeException("Unknown date mode: $this.")
        }
        val endDay = currentDate.dayOfMonth
        val endMonth = currentDate.monthValue
        val endYear = currentDate.year
        endDate = Date(endDay, endMonth, endYear)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExpensesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    companion object {
        private const val SCREEN_MODE = "mode"
        private const val DAY_MODE = "day"
        private const val WEEK_MODE = "week"
        private const val MONTH_MODE = "month"
        private const val YEAR_MODE = "year"
        private const val PERIOD_MODE = "period"
        private const val START_PERIOD_DATE_DAY = "periodStartDay"
        private const val END_PERIOD_DATE = "periodEnd"

        fun newInstanceNotPeriod(
            isDayMode: Boolean = false,
            isWeekMode: Boolean = false,
            isMonthMode: Boolean = false,
            isYearMode: Boolean = false
        ): ExpensesFragment {
            val screenMode = if (isDayMode) {
                DAY_MODE
            } else if (isWeekMode) {
                WEEK_MODE
            } else if (isMonthMode) {
                MONTH_MODE
            } else if (isYearMode) {
                YEAR_MODE
            } else {
                throw RuntimeException("Unknown screen mode: $this.")
            }
            return ExpensesFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, screenMode)
                }
            }
        }

        fun newInstancePeriod(startDate: Date, endDate: Date): ExpensesFragment {
            return ExpensesFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, PERIOD_MODE)
                    putParcelable(START_PERIOD_DATE_DAY, startDate)
                    putParcelable(END_PERIOD_DATE, endDate)
                }
            }
        }
    }
}