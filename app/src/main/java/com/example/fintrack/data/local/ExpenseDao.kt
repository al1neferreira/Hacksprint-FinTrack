package com.example.fintrack.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fintrack.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM all_transactions")
    fun getAllExpense(): Flow<List<Transaction>>

    @Query("SELECT * FROM all_transactions WHERE id = :id")
    fun getExpenseById(id: Int): Transaction?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExpense(transaction: Transaction): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun uptadeExpense(transaction: Transaction)

    @Delete
    fun deleteExpense(transaction: Transaction)

}