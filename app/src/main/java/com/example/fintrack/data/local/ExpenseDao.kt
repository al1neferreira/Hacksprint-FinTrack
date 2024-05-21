package com.example.fintrack.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fintrack.model.Transactions

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createExpense(transactions: Transactions)

    @Update
    suspend fun updateExpense(transactions: Transactions)

    @Delete
    suspend fun deleteExpense(transactions: Transactions)

    @Query("SELECT * FROM EXPENSE ORDER BY amount DESC")
    fun getAllExpenses(): LiveData<List<Transactions>>

    @Query("select *FROM Expense WHERE title LIKE :query OR category LIKE :query")
    fun searchExpense(query: String?): LiveData<List<Transactions>>


}