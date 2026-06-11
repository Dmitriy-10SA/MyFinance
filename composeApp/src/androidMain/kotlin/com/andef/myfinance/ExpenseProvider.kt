package com.andef.myfinance

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.core.net.toUri
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.andef.myfinance.core.domain.expense_common.expense_category.entities.BaseExpenseCategory
import com.andef.myfinance.core.utils.getters.now
import com.andef.myfinance.core.utils.mappers.localdate.localDateToInt
import com.andef.myfinance.db.AppDatabase
import com.andef.myfinance.db.ExpenseQueries
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlin.math.roundToLong

class ExpenseProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.andef.myfinance.expenseprovider"
        val CONTENT_URI: Uri = "content://$AUTHORITY/expenses".toUri()

        private const val AMOUNT = "amount"
        private const val DATE = "date"
        private const val NOTE = "note"
        private const val DB_NAME = "my-car-db"

        private const val EXPENSES = 1
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "expenses", EXPENSES)
        }
    }

    private lateinit var expenseQueries: ExpenseQueries

    override fun onCreate(): Boolean {
        val driver = AndroidSqliteDriver(AppDatabase.Schema, context!!, DB_NAME)
        expenseQueries = AppDatabase(driver).expenseQueries
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return when (uriMatcher.match(uri)) {
            EXPENSES -> {
                expenseQueries.insertExpense(
                    amount = ((values?.getAsDouble(AMOUNT) ?: 0.0) * 100).roundToLong(),
                    category = BaseExpenseCategory.TRANSPORT.title,
                    date = localDateToInt(
                        date = values?.getAsString(DATE)?.let {
                            java.time.LocalDate.parse(it).toKotlinLocalDate()
                        } ?: LocalDate.now()
                    ).toLong(),
                    note = values?.getAsString(NOTE) ?: ""
                )
                val id = expenseQueries.lastInsertRowId().executeAsOne()
                if (id > 0) {
                    val resultUri = ContentUris.withAppendedId(CONTENT_URI, id)
                    context?.contentResolver?.notifyChange(resultUri, null)
                    resultUri
                } else {
                    null
                }
            }

            else -> null
        }
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return -1
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return -1
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}