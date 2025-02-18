package com.andef.myfinance.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.andef.myfinance.R
import com.andef.myfinance.databinding.ActivityExpensesBinding
import com.andef.myfinance.domain.entities.Date
import com.andef.myfinance.domain.entities.ExpenseItem
import com.andef.myfinance.presentation.app.MyFinanceApplication
import com.andef.myfinance.presentation.factory.ViewModelFactory
import com.andef.myfinance.presentation.formatter.ItemDateFormatter
import com.andef.myfinance.presentation.viewmodel.expense.ExpensesViewModel
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import java.time.LocalDate
import java.util.Calendar
import javax.inject.Inject

class ExpensesActivity : AppCompatActivity(), OnSelectDateListener {
    private val binding by lazy {
        ActivityExpensesBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as MyFinanceApplication).component
    }

    private val currentDate = LocalDate.now()
    private var date: Date = Date(currentDate.dayOfMonth, currentDate.monthValue, currentDate.year)
    private var editTextExpenseEmpty = true
    private var typeIsChoose = false
    private var iconResId = -1
    private var type = ""

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ExpensesViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
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
                    editTextExpenseEmpty = true
                } else if (text.toString().isEmpty()) {
                    editTextExpenseEmpty = true
                } else {
                    editTextExpenseEmpty = false
                }
                showButtonAdd()
            }
            cardViewEat.setOnClickListener {
                colorCardView(isEat = true)
                iconResId = R.drawable.eat_svgrepo_com
                type = getString(R.string.eat)
                typeIsChoose = true
                showButtonAdd()
            }
            cardViewHealth.setOnClickListener {
                colorCardView(isHealth = true)
                iconResId = R.drawable.health_care_love_svgrepo_com
                type = getString(R.string.health)
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
            cardViewHome.setOnClickListener {
                colorCardView(isHome = true)
                iconResId = R.drawable.home_button_svgrepo_com
                type = getString(R.string.home)
                typeIsChoose = true
                showButtonAdd()
            }
            cardViewSport.setOnClickListener {
                colorCardView(isSport = true)
                iconResId = R.drawable.sport_trophy_svgrepo_com
                type = getString(R.string.sport)
                typeIsChoose = true
                showButtonAdd()
            }
            cardViewGraduate.setOnClickListener {
                colorCardView(isGraduate = true)
                iconResId = R.drawable.graduate_diploma_svgrepo_com
                type = getString(R.string.graduate)
                typeIsChoose = true
                showButtonAdd()
            }
            cardViewProducts.setOnClickListener {
                colorCardView(isProducts = true)
                iconResId = R.drawable.basket_svgrepo_com
                type = getString(R.string.products)
                typeIsChoose = true
                showButtonAdd()
            }
            cardViewTransport.setOnClickListener {
                colorCardView(isTransport = true)
                iconResId = R.drawable.public_transport_taxi_car_cab_svgrepo_com
                type = getString(R.string.transport)
                typeIsChoose = true
                showButtonAdd()
            }
            cardViewDate.setOnClickListener {
                openDatePicker()
            }
            buttonAdd.setOnClickListener {
                if (iconResId != -1 && type != "") {
                    val expenseItem = ExpenseItem(
                        0,
                        iconResId,
                        type,
                        editTextPrice.text.toString().trim().toDouble(),
                        editTextComment.text.toString().trim(),
                        ItemDateFormatter.formatDate(date)
                    )
                    viewModel.addExpense(expenseItem)
                } else {
                    Toast.makeText(
                        this@ExpensesActivity,
                        R.string.input_empty_expense,
                        Toast.LENGTH_SHORT
                    ).show()
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
        isTransport: Boolean = false,
        isProducts: Boolean = false,
        isGraduate: Boolean = false,
        isSport: Boolean = false,
        isHome: Boolean = false,
        isHealth: Boolean = false,
        isGift: Boolean = false,
        isEat: Boolean = false
    ) {
        with(binding) {
            cardViewEat.setCardBackgroundColor(getColor(R.color.white))
            cardViewHealth.setCardBackgroundColor(getColor(R.color.white))
            cardViewGift.setCardBackgroundColor(getColor(R.color.white))
            cardViewHome.setCardBackgroundColor(getColor(R.color.white))
            cardViewSport.setCardBackgroundColor(getColor(R.color.white))
            cardViewGraduate.setCardBackgroundColor(getColor(R.color.white))
            cardViewProducts.setCardBackgroundColor(getColor(R.color.white))
            cardViewTransport.setCardBackgroundColor(getColor(R.color.white))
            if (isTransport) {
                cardViewTransport.setCardBackgroundColor(getColor(R.color.my_blue_light))
            } else if (isProducts) {
                cardViewProducts.setCardBackgroundColor(getColor(R.color.my_blue_light))
            } else if (isGraduate) {
                cardViewGraduate.setCardBackgroundColor(getColor(R.color.my_blue_light))
            } else if (isSport) {
                cardViewSport.setCardBackgroundColor(getColor(R.color.my_blue_light))
            } else if (isHome) {
                cardViewHome.setCardBackgroundColor(getColor(R.color.my_blue_light))
            } else if (isHealth) {
                cardViewHealth.setCardBackgroundColor(getColor(R.color.my_blue_light))
            } else if (isGift) {
                cardViewGift.setCardBackgroundColor(getColor(R.color.my_blue_light))
            } else if (isEat) {
                cardViewEat.setCardBackgroundColor(getColor(R.color.my_blue_light))
            }
        }
    }

    private fun showButtonAdd() {
        if (typeIsChoose && !editTextExpenseEmpty) {
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
        fun newIntent(context: Context): Intent {
            return Intent(context, ExpensesActivity::class.java)
        }
    }
}