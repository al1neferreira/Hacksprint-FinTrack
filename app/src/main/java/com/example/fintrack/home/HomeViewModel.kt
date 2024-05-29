package com.example.fintrack.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintrack.model.Transaction
import com.example.fintrack.repo.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: ExpenseRepository) : ViewModel() {
    private val _expenseData = MutableLiveData<List<Transaction>>()
    val expenseData: LiveData<List<Transaction>> get() = _expenseData

    fun addExpenseData(transaction: Transaction) {
        val currentList = _expenseData.value?.toMutableList() ?: mutableListOf()
        currentList.add(transaction)
        _expenseData.postValue(currentList)

    }

    fun getTransactions(): Flow<List<Transaction>> {
        return repository.getAllExpense()
    }

    fun updateTransactions(transactions: List<Transaction>) {
        _expenseData.postValue(transactions)
    }

    fun deleteAllExpenses() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
            _expenseData.postValue(emptyList())
        }
    }

    fun deleteExpense(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteExpense(transaction)
        }
    }

    fun getExpenseById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val expense = repository.getExpenseById(id)
            expense?.let {
                withContext(Dispatchers.Main) {
                    _expenseData.value = listOf(it)
                }
            }
        }
    }

    fun updateExpense(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateExpense(transaction)
        }
    }
}