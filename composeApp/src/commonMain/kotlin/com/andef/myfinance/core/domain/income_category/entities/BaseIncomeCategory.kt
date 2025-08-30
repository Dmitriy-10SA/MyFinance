package com.andef.myfinance.core.domain.income_category.entities

enum class BaseIncomeCategory(val title: String, val titleForUser: String) {
    SALARY(title = "SALARY", titleForUser = "Зарплата"),
    BANK(title = "BANK", titleForUser = "Банк"),
    LUCK(title = "LUCK", titleForUser = "Удача"),
    GIFTS(title = "GIFTS", titleForUser = "Подарки"),
    OTHER(title = "OTHER", titleForUser = "Другое")
}