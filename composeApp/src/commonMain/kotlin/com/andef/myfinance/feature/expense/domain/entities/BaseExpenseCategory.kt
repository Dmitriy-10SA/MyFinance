package com.andef.myfinance.feature.expense.domain.entities

enum class BaseExpenseCategory(val title: String, val titleForUser: String) {
    PRODUCTS(title = "PRODUCTS", titleForUser = "Продукты"),
    CAFE(title = "CAFE", titleForUser = "Кафе"),
    HOME(title = "HOME", titleForUser = "Дом"),
    GIFTS(title = "GIFTS", titleForUser = "Подарки"),
    STUDY(title = "STUDY", titleForUser = "Учеба"),
    HEALTH(title = "HEALTH", titleForUser = "Здоровье"),
    TRANSPORT(title = "TRANSPORT", titleForUser = "Транспорт"),
    SPORT(title = "SPORT", titleForUser = "Спорт"),
    CLOTHES(title = "CLOTHES", titleForUser = "Одежда"),
    OTHER(title = "OTHER", titleForUser = "Другое")
}