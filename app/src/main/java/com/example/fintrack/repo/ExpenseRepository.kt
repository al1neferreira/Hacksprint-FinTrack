package com.example.fintrack.repo

import com.example.fintrack.db.ExpenseDatabase
import com.example.fintrack.home.transactions
import com.example.fintrack.model.Transaction


class ExpenseRepository(private val db: ExpenseDatabase) {

    //insert transaction
    suspend fun insertExpense(transaction: Transaction) =
        db.getExpenseDao().insertExpense(transaction)

    //update transaction
    suspend fun updateExpense(transaction: Transaction) =
        db.getExpenseDao().updateExpense(transaction)

    //delete transaction
    suspend fun deleteExpense(transaction: Transaction) =
        db.getExpenseDao().deleteExpense(transaction)

    //get all transaction
    fun getAllExpense() = db.getExpenseDao().getAllExpense()

    //get single transaction by id
    fun getExpenseById(id: Int) = db.getExpenseDao().getExpenseById(id)

    suspend fun deleteAll() = db.getExpenseDao().deleteAll()

    fun insertAll() = db.getExpenseDao().insertAll(transactions)

}