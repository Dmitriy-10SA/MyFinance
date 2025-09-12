package com.andef.myfinance.core.utils.getters

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.andef.myfinance.core.domain.expense_common.expense_category.entities.BaseExpenseCategory
import myfinance.composeapp.generated.resources.Res
import myfinance.composeapp.generated.resources.my_finance_cafe
import myfinance.composeapp.generated.resources.my_finance_clothes
import myfinance.composeapp.generated.resources.my_finance_gifts
import myfinance.composeapp.generated.resources.my_finance_health
import myfinance.composeapp.generated.resources.my_finance_home
import myfinance.composeapp.generated.resources.my_finance_other
import myfinance.composeapp.generated.resources.my_finance_products
import myfinance.composeapp.generated.resources.my_finance_sport
import myfinance.composeapp.generated.resources.my_finance_study
import myfinance.composeapp.generated.resources.my_finance_transport
import org.jetbrains.compose.resources.painterResource

fun getTitleForExpense(category: String): String {
    return when (category) {
        BaseExpenseCategory.PRODUCTS.title -> BaseExpenseCategory.PRODUCTS.titleForUser
        BaseExpenseCategory.CAFE.title -> BaseExpenseCategory.CAFE.titleForUser
        BaseExpenseCategory.HOME.title -> BaseExpenseCategory.HOME.titleForUser
        BaseExpenseCategory.GIFTS.title -> BaseExpenseCategory.GIFTS.titleForUser
        BaseExpenseCategory.STUDY.title -> BaseExpenseCategory.STUDY.titleForUser
        BaseExpenseCategory.HEALTH.title -> BaseExpenseCategory.HEALTH.titleForUser
        BaseExpenseCategory.TRANSPORT.title -> BaseExpenseCategory.TRANSPORT.titleForUser
        BaseExpenseCategory.SPORT.title -> BaseExpenseCategory.SPORT.titleForUser
        BaseExpenseCategory.CLOTHES.title -> BaseExpenseCategory.CLOTHES.titleForUser
        BaseExpenseCategory.OTHER.title -> BaseExpenseCategory.OTHER.titleForUser
        else -> category
    }
}

@Composable
fun getImageForExpense(category: String): Painter? {
    return when (category) {
        BaseExpenseCategory.PRODUCTS.title -> painterResource(Res.drawable.my_finance_products)
        BaseExpenseCategory.CAFE.title -> painterResource(Res.drawable.my_finance_cafe)
        BaseExpenseCategory.HOME.title -> painterResource(Res.drawable.my_finance_home)
        BaseExpenseCategory.GIFTS.title -> painterResource(Res.drawable.my_finance_gifts)
        BaseExpenseCategory.STUDY.title -> painterResource(Res.drawable.my_finance_study)
        BaseExpenseCategory.HEALTH.title -> painterResource(Res.drawable.my_finance_health)
        BaseExpenseCategory.TRANSPORT.title -> painterResource(Res.drawable.my_finance_transport)
        BaseExpenseCategory.SPORT.title -> painterResource(Res.drawable.my_finance_sport)
        BaseExpenseCategory.CLOTHES.title -> painterResource(Res.drawable.my_finance_clothes)
        BaseExpenseCategory.OTHER.title -> painterResource(Res.drawable.my_finance_other)
        else -> null
    }
}