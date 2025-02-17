package com.andef.myfinance.data.database.expense

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andef.myfinance.data.dao.income.ExpenseDao
import com.andef.myfinance.di.annotation.ApplicationScope

@ApplicationScope
@Database(entities = [ExpenseItemDbModel::class], version = 1, exportSchema = false)
abstract class ExpensesDataBase: RoomDatabase() {
    abstract val expenseDao: ExpenseDao

    companion object {
        private var instance: ExpensesDataBase? = null
        fun getInstance(application: Application): ExpensesDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    application,
                    ExpensesDataBase::class.java,
                    DB_NAME
                ).build()
            }
            return instance!!
        }

        private const val DB_NAME = "expenses"
    }
}