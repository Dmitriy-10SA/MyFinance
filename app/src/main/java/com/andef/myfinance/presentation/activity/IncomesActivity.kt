package com.andef.myfinance.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.andef.myfinance.R
import com.andef.myfinance.databinding.ActivityIncomesBinding
import com.andef.myfinance.domain.entities.Date
import com.andef.myfinance.domain.entities.IncomeItem
import com.andef.myfinance.presentation.app.MyFinanceApplication
import com.andef.myfinance.presentation.factory.ViewModelFactory
import com.andef.myfinance.presentation.formatter.ItemDateFormatter
import com.andef.myfinance.presentation.viewmodel.income.IncomesViewModel
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import java.time.LocalDate
import java.util.Calendar
import javax.inject.Inject

class IncomesActivity : AppCompatActivity(), OnSelectDateListener {
    private val binding by lazy {
        ActivityIncomesBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[IncomesViewModel::class.java]
    }

    private val currentDate = LocalDate.now()
    private var date: Date = Date(currentDate.dayOfMonth, currentDate.monthValue, currentDate.year)
    private var editTextPriceEmpty = true
    private var typeIsChoose = false
    private var iconResId = -1
    private var type = ""
    private val screenMode by lazy {
        intent.extras!!.getString(EXTRA_SCREEN_MODE)
    }

    private val incomeItemFromExtra by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.extras!!.getParcelable(EXTRA_INCOME_ITEM, IncomeItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.extras!!.getParcelable(EXTRA_INCOME_ITEM)
        }
    }

    private val component by lazy {
        (application as MyFinanceApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        if (screenMode == EXTRA_CHANGE_AND_REMOVE_MODE) {
            additionalInitView()
        }
    }

    private fun additionalInitView() {
        if (incomeItemFromExtra != null) {
            with(binding) {
                textViewChangeIncome.setText(R.string.change_expense)
                buttonAdd.setText(R.string.change)
                floatingActionButtonRemove.visibility = VISIBLE
                type = incomeItemFromExtra!!.type
                iconResId = incomeItemFromExtra!!.iconResId
                val tmpDate = incomeItemFromExtra!!.dateString.split("/").map { it.toInt() }
                date = Date(
                    tmpDate[0],
                    tmpDate[1],
                    tmpDate[2]
                )
                if (iconResId == R.drawable.salesman_salesman_svgrepo_com) {
                    colorCardView(isSalary = true)
                    typeIsChoose = true
                } else if (iconResId == R.drawable.bank_svgrepo_com) {
                    colorCardView(isBank = true)
                    typeIsChoose = true
                } else if (iconResId == R.drawable.casino_slot_lucky_seven_svgrepo_com) {
                    colorCardView(isLucky = true)
                    typeIsChoose = true
                } else if (iconResId == R.drawable.gift_svgrepo_com) {
                    colorCardView(isGift = true)
                    typeIsChoose = true
                }
                editTextPrice.setText("${incomeItemFromExtra!!.income}")
                editTextPriceEmpty = false
                editTextComment.setText(incomeItemFromExtra!!.comment)
            }
        }
    }

    override fun onSelect(calendar: List<Calendar>) {
        val selectedDate = calendar[0]
        date = Date(
            selectedDate[Calendar.DAY_OF_MONTH],
            selectedDate[Calendar.MONTH] + 1,
            selectedDate[Calendar.YEAR]
        )
    }

    private fun openDatePicker() {
        DatePickerBuilder(this, this)
            .pickerType(CalendarView.ONE_DAY_PICKER)
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

    private fun initViews() {
        with(binding) {
            editTextPrice.addTextChangedListener { text ->
                if (text.toString().isNotEmpty()
                    && (text.toString()[0] == '.' || text.toString()[0] == '0')
                ) {
                    editTextPrice.setText("")
                    editTextPriceEmpty = true
                } else if (text.toString().isEmpty()) {
                    editTextPriceEmpty = true
                } else {
                    editTextPriceEmpty = false
                }
                showButtonAdd()
            }
            cardViewSalary.setOnClickListener {
                colorCardView(isSalary = true)
                iconResId = R.drawable.salesman_salesman_svgrepo_com
                type = getString(R.string.salary)
                typeIsChoose = true
                showButtonAdd()
            }
            cardViewBank.setOnClickListener {
                colorCardView(isBank = true)
                iconResId = R.drawable.bank_svgrepo_com
                type = getString(R.string.bank)
                typeIsChoose = true
                showButtonAdd()
            }
            cardViewGift.setOnClickListener {
                colorCardView(isGift = true)
                iconResId = R.drawable.gift_svgrepo_com
                type = getString(R.string.gift)
                typeIsChoose = true
                showButtonAdd()
            }
            cardViewLucky.setOnClickListener {
                colorCardView(isLucky = true)
                iconResId = R.drawable.casino_slot_lucky_seven_svgrepo_com
                type = getString(R.string.lucky)
                typeIsChoose = true
                showButtonAdd()
            }
            cardViewDate.setOnClickListener {
                openDatePicker()
            }
            buttonAdd.setOnClickListener {
                if (iconResId != -1 && type != "") {
                    val incomeItem = IncomeItem(
                        0,
                        iconResId,
                        type,
                        editTextPrice.text.toString().trim().toDouble(),
                        editTextComment.text.toString().trim(),
                        ItemDateFormatter.formatDate(date)
                    )
                    if (screenMode == EXTRA_ADD_MODE) {
                        viewModel.addIncome(incomeItem)
                    } else if (screenMode == EXTRA_CHANGE_AND_REMOVE_MODE) {
                        viewModel.changeIncome(incomeItemFromExtra!!.id, incomeItem)
                    }
                } else {
                    Toast.makeText(
                        this@IncomesActivity,
                        R.string.input_empty_expense,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            floatingActionButtonRemove.setOnClickListener {
                if (screenMode == EXTRA_CHANGE_AND_REMOVE_MODE) {
                    viewModel.removeIncome(incomeItemFromExtra!!.id)
                }
            }
        }
        viewModel.isSuccessAdd.observe(this) {
            if (it) {
                finish()
            } else {
                Toast.makeText(this, R.string.exception, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun colorCardView(
        isSalary: Boolean = false,
        isBank: Boolean = false,
        isGift: Boolean = false,
        isLucky: Boolean = false
    ) {
        with(binding) {
            cardViewSalary.setCardBackgroundColor(getColor(R.color.white))
            cardViewBank.setCardBackgroundColor(getColor(R.color.white))
            cardViewGift.setCardBackgroundColor(getColor(R.color.white))
            cardViewLucky.setCardBackgroundColor(getColor(R.color.white))
            if (isGift) {
                cardViewGift.setCardBackgroundColor(getColor(R.color.my_blue_light))
            } else if (isLucky) {
                cardViewLucky.setCardBackgroundColor(getColor(R.color.my_blue_light))
            } else if (isBank) {
                cardViewBank.setCardBackgroundColor(getColor(R.color.my_blue_light))
            } else if (isSalary) {
                cardViewSalary.setCardBackgroundColor(getColor(R.color.my_blue_light))
            }
        }
    }

    private fun showButtonAdd() {
        if (typeIsChoose && !editTextPriceEmpty) {
            with(binding) {
                buttonAdd.isEnabled = true
                buttonAdd.alpha = 1f
            }
        } else {
            with(binding) {
                buttonAdd.isEnabled = false
                buttonAdd.alpha = 0.5f
            }
        }
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "screenMode"
        private const val EXTRA_ADD_MODE = "add"
        private const val EXTRA_CHANGE_AND_REMOVE_MODE = "change"
        private const val EXTRA_INCOME_ITEM = "expenseItem"

        fun newIntent(context: Context): Intent {
            return Intent(context, IncomesActivity::class.java).apply {
                putExtra(EXTRA_SCREEN_MODE, EXTRA_ADD_MODE)
            }
        }

        fun newIntentChangeAndRemove(context: Context, incomeItem: IncomeItem): Intent {
            return Intent(context, IncomesActivity::class.java).apply {
                putExtra(
                    EXTRA_SCREEN_MODE,
                    EXTRA_CHANGE_AND_REMOVE_MODE
                )
                putExtra(EXTRA_INCOME_ITEM, incomeItem)
            }
        }
    }
}