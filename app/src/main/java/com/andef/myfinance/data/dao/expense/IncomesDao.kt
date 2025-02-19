package com.andef.myfinance.data.dao.expense

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andef.myfinance.data.database.income.IncomeItemDbModel

@Dao
interface IncomesDao {
    @Query("SELECT * FROM incomes WHERE dateString = :dateString")
    fun getIncomesByDay(dateString: String): LiveData<List<IncomeItemDbModel>>

    @Query(
        """
        SELECT * FROM incomes 
        WHERE 
            SUBSTR(dateString, 7, 4) || '-' || 
            SUBSTR(dateString, 4, 2) || '-' || 
            SUBSTR(dateString, 1, 2) 
            BETWEEN 
                SUBSTR(:startDateString, 7, 4) || '-' || 
                SUBSTR(:startDateString, 4, 2) || '-' || 
                SUBSTR(:startDateString, 1, 2)
            AND 
                SUBSTR(:endDateString, 7, 4) || '-' || 
                SUBSTR(:endDateString, 4, 2) || '-' || 
                SUBSTR(:endDateString, 1, 2)
    """
    )
    fun getIncomesByPeriod(
        startDateString: String,
        endDateString: String
    ): LiveData<List<IncomeItemDbModel>>

    @Insert
    suspend fun addIncome(incomeItem: IncomeItemDbModel)

    @Query("DELETE FROM incomes WHERE id = :id")
    suspend fun removeIncome(id: Int)

    @Query("SELECT SUM(income) FROM incomes WHERE dateString = :dateString")
    fun getFullIncomeByDay(dateString: String): LiveData<Double>

    @Query(
        """
        SELECT SUM(income) FROM incomes
        WHERE 
            SUBSTR(dateString, 7, 4) || '-' ||
            SUBSTR(dateString, 4, 2) || '-' || 
            SUBSTR(dateString, 1, 2) 
            BETWEEN 
                SUBSTR(:startDateString, 7, 4) || '-' || 
                SUBSTR(:startDateString, 4, 2) || '-' || 
                SUBSTR(:startDateString, 1, 2)
            AND 
                SUBSTR(:endDateString, 7, 4) || '-' || 
                SUBSTR(:endDateString, 4, 2) || '-' || 
                SUBSTR(:endDateString, 1, 2)
    """
    )
    fun getFullIncomeByPeriod(
        startDateString: String,
        endDateString: String
    ): LiveData<Double>
}