package com.example.fintrack.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fintrack.db.ExpenseDao
import com.example.fintrack.model.Transaction
import kotlinx.coroutines.flow.Flow

class HomeViewModel() : ViewModel() {
    private val _expenseData = MutableLiveData<List<Transaction>>()
    val expenseData: LiveData<List<Transaction>> get() = _expenseData

    fun addExpenseData(transaction: Transaction) {
        val currentList = _expenseData.value?.toMutableList() ?: mutableListOf()
        currentList.add(transaction)
        _expenseData.postValue(currentList)

    }

    fun getTransactions(expenseDao: ExpenseDao): Flow<List<Transaction>> {
        return expenseDao.getAllExpense()
    }

    fun updateTransactions(transactions: List<Transaction>) {
        _expenseData.postValue(transactions)
    }
}