package com.example.fintrack.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fintrack.model.Transaction
import com.example.fintrack.repository.ExpenseRepository
import kotlinx.coroutines.launch

class ExpenseViewModel(app:Application,private val expenseRepository: ExpenseRepository):AndroidViewModel(app) {

    fun insertExpense(transactions: Transaction)=
        viewModelScope.launch {
            expenseRepository.createExpense(transactions)
        }

    fun deleteExpense(transactions: Transaction) =
        viewModelScope.launch {
            expenseRepository.deleteExpense(transactions)
        }

    fun updateExpense(transactions: Transaction) =
        viewModelScope.launch {
            expenseRepository.updateExpense(transactions)

        }

    fun getAllExpenses() = expenseRepository.getAllExpenses()

    fun searchExpense(query: String?) =
        expenseRepository.searchExpense(query)
}