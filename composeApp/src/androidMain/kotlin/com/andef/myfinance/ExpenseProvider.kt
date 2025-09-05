package com.andef.myfinance

import android.app.Application
import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.andef.myfinance.core.domain.expense_common.expense_category.entities.BaseExpenseCategory
import com.andef.myfinance.core.utils.mappers.localdate.localDateToInt
import com.andef.myfinance.db.AppDatabase
import com.andef.myfinance.db.ExpenseQueries
import kotlinx.datetime.toKotlinLocalDate
import java.time.LocalDate

class ExpenseProvider : ContentProvider() {
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITY, PATH, INSERT_EXPENSE)
    }

    lateinit var expenseQueries: ExpenseQueries

    override fun onCreate(): Boolean {
        expenseQueries = ExpenseQueries(
            AndroidSqliteDriver(
                AppDatabase.Schema,
                context as Application,
                "my-car-db"
            )
        )
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String?>?,
        selection: String?,
        selectionArgs: Array<out String?>?,
        sortOrder: String?
    ): Cursor? = null

    override fun getType(uri: Uri): String? = null

    override fun insert(
        uri: Uri,
        values: ContentValues?
    ): Uri? {
        return when (uriMatcher.match(uri)) {
            INSERT_EXPENSE -> {
                if (values == null) {
                    null
                } else {
                    try {
                        val amount = values.getAsDouble(AMOUNT)
                        val date = LocalDate.parse(values.getAsString(DATE))
                        val note = values.getAsString(NOTE)
                        expenseQueries.insertExpense(
                            amount = amount,
                            category = BaseExpenseCategory.TRANSPORT.title,
                            date = localDateToInt(date.toKotlinLocalDate()).toLong(),
                            note = note
                        )
                        ContentUris.withAppendedId(
                            uri,
                            expenseQueries.lastInsertRowId().executeAsOne()
                        )
                    } catch (e: Exception) {
                        null
                    }
                }
            }

            else -> null
        }
    }

    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<out String?>?
    ): Int = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String?>?
    ): Int = 0

    companion object {
        const val AMOUNT = "amount"
        const val DATE = "date"
        const val NOTE = "note"

        const val AUTHORITY = "com.andef.myfinance.expenseprovider"
        const val PATH = "expenses"
        const val INSERT_EXPENSE = 100
    }
}