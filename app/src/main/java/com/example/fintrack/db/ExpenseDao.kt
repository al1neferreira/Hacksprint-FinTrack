package com.example.fintrack.db

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

    //get all saved transaction list
    @Query("SELECT * FROM all_transactions")
    fun getAllExpense(): Flow<List<Transaction>>

    //get an expense by id
    @Query("SELECT * FROM all_transactions WHERE id = :id")
    fun getExpenseById(id: Int): Transaction?

    @Query("DELETE FROM all_transactions")
    fun deleteAll()

    //used to insert new transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExpense(vararg transaction: Transaction)

    //used to update existing transaction
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateExpense(transaction: Transaction)

    //used to delete transaction
    @Delete
    fun deleteExpense(transaction: Transaction)

}