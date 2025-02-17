package com.andef.myfinance.data.database.income

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andef.myfinance.data.dao.expense.IncomesDao
import com.andef.myfinance.di.annotation.ApplicationScope

@ApplicationScope
@Database(entities = [IncomeItemDbModel::class], version = 1, exportSchema = false)
abstract class IncomesDataBase : RoomDatabase() {
    abstract val incomesDao: IncomesDao

    companion object {
        private var instance: IncomesDataBase? = null
        fun getInstance(application: Application): IncomesDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    application,
                    IncomesDataBase::class.java,
                    DB_NAME
                ).build()
            }
            return instance!!
        }

        private const val DB_NAME = "incomes"
    }
}