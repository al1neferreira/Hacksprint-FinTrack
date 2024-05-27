package com.example.fintrack.repository

import com.example.fintrack.data.local.ExpenseDatabase
import com.example.fintrack.model.Transaction


class ExpenseRepository(private val db: ExpenseDatabase) {

    suspend fun insertExpense(transaction: Transaction) =
        db.getExpenseDao().insertExpense(transaction)

    suspend fun uptadeExpense(transaction: Transaction) =
        db.getExpenseDao().uptadeExpense(transaction)

    suspend fun deleteExpense(transaction: Transaction) =
        db.getExpenseDao().deleteExpense(transaction)

    fun getAllExpense() = db.getExpenseDao().getAllExpense()

    fun getExpenseById(id: Int) = db.getExpenseDao().getExpenseById(id)

}