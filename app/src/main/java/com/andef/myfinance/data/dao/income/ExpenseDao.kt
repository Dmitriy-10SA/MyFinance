package com.andef.myfinance.data.dao.income

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andef.myfinance.data.database.expense.ExpenseItemDbModel

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses WHERE dateString = :dateString")
    fun getExpensesByDay(dateString: String): LiveData<List<ExpenseItemDbModel>>

    @Query(
        """
        SELECT * FROM expenses 
        WHERE 
            SUBSTR(dateString, 7, 4) || '-' || 
            SUBSTR(dateString, 4, 2) || '-' || 
            SUBSTR(dateString, 1, 2) 
            BETWEEN :startDateString AND :endDateString
    """
    )
    fun getExpensesByPeriod(
        startDateString: String,
        endDateString: String
    ): LiveData<List<ExpenseItemDbModel>>

    @Insert
    suspend fun addExpense(expenseItem: ExpenseItemDbModel)

    @Query("DELETE FROM expenses WHERE id = :id")
    suspend fun removeExpense(id: Int)

    @Query("SELECT SUM(price) FROM expenses WHERE dateString = :dateString")
    fun getFullExpenseByDay(dateString: String): LiveData<Double>

    @Query(
        """
        SELECT SUM(price) FROM expenses
        WHERE 
            SUBSTR(dateString, 7, 4) || '-' ||
            SUBSTR(dateString, 4, 2) || '-' || 
            SUBSTR(dateString, 1, 2) 
            BETWEEN :startDateString AND :endDateString
    """
    )
    fun getFullExpenseByPeriod(
        startDateString: String,
        endDateString: String
    ): LiveData<Double>
}