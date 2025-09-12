package com.andef.myfinance.core.utils.getters

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.andef.myfinance.core.domain.income_common.income_category.entities.BaseIncomeCategory
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_bank
import myfinance.composeapp.generated.resources.my_finance_gifts
import myfinance.composeapp.generated.resources.my_finance_luck
import myfinance.composeapp.generated.resources.my_finance_other
import myfinance.composeapp.generated.resources.my_finance_salary
import org.jetbrains.compose.resources.painterResource

fun getTitleForIncome(category: String): String {
    return when (category) {
        BaseIncomeCategory.SALARY.title -> BaseIncomeCategory.SALARY.titleForUser
        BaseIncomeCategory.BANK.title -> BaseIncomeCategory.BANK.titleForUser
        BaseIncomeCategory.LUCK.title -> BaseIncomeCategory.LUCK.titleForUser
        BaseIncomeCategory.GIFTS.title -> BaseIncomeCategory.GIFTS.titleForUser
        BaseIncomeCategory.OTHER.title -> BaseIncomeCategory.OTHER.titleForUser
        else -> category
    }
}

@Composable
fun getImageForIncome(category: String): Painter? {
    return when (category) {
        BaseIncomeCategory.SALARY.title -> painterResource(Res.drawable.my_finance_salary)
        BaseIncomeCategory.BANK.title -> painterResource(Res.drawable.my_finance_bank)
        BaseIncomeCategory.LUCK.title -> painterResource(Res.drawable.my_finance_luck)
        BaseIncomeCategory.GIFTS.title -> painterResource(Res.drawable.my_finance_gifts)
        BaseIncomeCategory.OTHER.title -> painterResource(Res.drawable.my_finance_other)
        else -> null
    }
}