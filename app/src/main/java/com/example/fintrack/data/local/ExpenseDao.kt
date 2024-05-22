package com.example.fintrack.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fintrack.model.Transaction

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createExpense(transactions: Transaction)

    @Update
    suspend fun updateExpense(transactions: Transaction)

    @Delete
    suspend fun deleteExpense(transactions: Transaction)

    @Query("SELECT * FROM EXPENSE ORDER BY amount DESC")
    fun getAllExpenses(): LiveData<List<Transaction>>

    @Query("SELECT * FROM EXPENSE WHERE category LIKE :query")
    fun searchExpense(query: String?): LiveData<List<Transaction>>


}