package com.andef.myfinance.presentation.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.andef.myfinance.R
import com.andef.myfinance.databinding.FragmentIncomesBinding
import com.andef.myfinance.domain.entities.Date
import com.andef.myfinance.domain.entities.IncomeItem
import com.andef.myfinance.presentation.activity.IncomesActivity
import com.andef.myfinance.presentation.adapter.income.IncomesAdapter
import com.andef.myfinance.presentation.app.MyFinanceApplication
import com.andef.myfinance.presentation.factory.ViewModelFactory
import com.andef.myfinance.presentation.formatter.DateFormatterWithDos
import com.andef.myfinance.presentation.formatter.PriceAndIncomeFormatter
import com.andef.myfinance.presentation.viewmodel.income.IncomesFragmentViewModel
import java.time.LocalDate
import javax.inject.Inject

class IncomesFragment : Fragment() {
    private var _binding: FragmentIncomesBinding? = null
    private val binding get() = _binding!!

    private lateinit var screenMode: String
    private lateinit var startDate: Date
    private lateinit var endDate: Date

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[IncomesFragmentViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as MyFinanceApplication).component
    }

    private lateinit var incomesAdapter: IncomesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
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
        _binding = FragmentIncomesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        with(binding) {
            if (screenMode == DAY_MODE) {
                textViewDate.setText(R.string.today)
            } else {
                val formatStartDate = DateFormatterWithDos.formatDate(startDate)
                val formatEndDate = DateFormatterWithDos.formatDate(endDate)
                textViewDate.text = "$formatStartDate - $formatEndDate"
            }
            floatingActionButtonAddIncome.setOnClickListener {
                incomesScreen()
            }
            incomesAdapter = IncomesAdapter()
            incomesAdapter.setOnIncomeItemClickListeners { incomeItem ->
                incomesAddAndChangeScreen(incomeItem)
            }
            recyclerViewIncomes.adapter = incomesAdapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViewModel() {
        if (screenMode == DAY_MODE) {
            viewModel.getIncomesByDay(startDate).observe(viewLifecycleOwner) {
                incomesAdapter.submitList(it)
            }
            viewModel.getFullIncomeByDay(startDate).observe(viewLifecycleOwner) {
                if (it != null) {
                    binding.textViewAllIncomes.text =
                        "${getString(R.string.total)} ${PriceAndIncomeFormatter.formatPrice(it)}"
                } else {
                    binding.textViewAllIncomes.text = "${getString(R.string.total)} 0₽"
                }
            }
        } else {
            viewModel.getIncomesByPeriod(startDate, endDate).observe(viewLifecycleOwner) {
                incomesAdapter.submitList(it)
            }
            viewModel.getFullIncomeByPeriod(startDate, endDate).observe(viewLifecycleOwner) {
                if (it != null) {
                    binding.textViewAllIncomes.text =
                        "${getString(R.string.total)} ${PriceAndIncomeFormatter.formatPrice(it)}"
                } else {
                    binding.textViewAllIncomes.text = "${getString(R.string.total)} 0₽"
                }
            }
        }
    }

    private fun incomesAddAndChangeScreen(incomeItem: IncomeItem) {
        val intent = IncomesActivity.newIntentChangeAndRemove(requireContext(), incomeItem)
        startActivity(intent)
    }

    private fun incomesScreen() {
        val intent = IncomesActivity.newIntent(requireContext())
        startActivity(intent)
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
        ): IncomesFragment {
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
            return IncomesFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, screenMode)
                }
            }
        }

        fun newInstancePeriod(startDate: Date, endDate: Date): IncomesFragment {
            return IncomesFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, PERIOD_MODE)
                    putParcelable(START_PERIOD_DATE_DAY, startDate)
                    putParcelable(END_PERIOD_DATE, endDate)
                }
            }
        }
    }
}