package com.example.fintrack.repository

import androidx.room.RawQuery
import com.example.fintrack.data.local.ExpenseDatabase
import com.example.fintrack.model.Transaction

class ExpenseRepository(private val db:ExpenseDatabase) {

    suspend fun createExpense(transactions: Transaction) = db.getExpenseDao().createExpense(transactions)
    suspend fun updateExpense(transactions: Transaction) = db.getExpenseDao().updateExpense(transactions)
    suspend fun deleteExpense(transactions: Transaction)=db.getExpenseDao().deleteExpense(transactions)

    fun getAllExpenses() = db.getExpenseDao().getAllExpenses()
    fun searchExpense(query: String?) = db.getExpenseDao().searchExpense(query)
}