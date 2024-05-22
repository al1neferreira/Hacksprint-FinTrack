package com.example.fintrack.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintrack.model.Transactions
import com.example.fintrack.repository.ExpenseRepository
import kotlinx.coroutines.launch

class ExpenseViewModel(app:Application,private val expenseRepository: ExpenseRepository):AndroidViewModel(app) {

    fun addExpense(transactions: Transactions)=
        viewModelScope.launch {
            expenseRepository.createExpense(transactions)
        }

    fun deleteExpense(transactions: Transactions) =
        viewModelScope.launch {
            expenseRepository.updateExpense(transactions)
        }

    fun getAllExpenses() = expenseRepository.getAllExpenses()

    fun searchExpense(query: String?) =
        expenseRepository.searchExpense(query)
}