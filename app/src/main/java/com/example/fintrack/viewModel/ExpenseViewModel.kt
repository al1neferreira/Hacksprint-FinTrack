package com.example.fintrack.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintrack.model.Transaction
import com.example.fintrack.repo.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application, private val repository: ExpenseRepository) :
    AndroidViewModel(application) {

    val allExpenses:Flow<List<Transaction>> = repository.getAllExpense()

    fun insertExpense(transaction: Transaction) = viewModelScope.launch {
        repository.insertExpense(transaction)
    }

    fun updateExpense(transaction: Transaction) = viewModelScope.launch {
        repository.updateExpense(transaction)
    }

    fun deleteExpense(transaction: Transaction) = viewModelScope.launch {
        repository.deleteExpense(transaction)
    }
}