package com.example.fintrack.repository

import androidx.room.RawQuery
import com.example.fintrack.data.local.ExpenseDatabase
import com.example.fintrack.model.Transactions

class ExpenseRepository(private val db:ExpenseDatabase) {

    suspend fun createExpense(transactions: Transactions) = db.getExpenseDao().createExpense(transactions)
    suspend fun updateExpense(transactions: Transactions) = db.getExpenseDao().updateExpense(transactions)
    suspend fun deleteExpense(transactions: Transactions)=db.getExpenseDao().deleteExpense(transactions)

    fun getAllExpenses() = db.getExpenseDao().getAllExpenses()
    fun searchExpense(query: String?) = db.getExpenseDao().searchExpense(query)
}