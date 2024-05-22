package com.example.fintrack.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fintrack.model.Transaction

class HomeViewModel : ViewModel() {
    private val _expenseData = MutableLiveData<List<Transaction>>()
    val expenseData: LiveData<List<Transaction>> get() = _expenseData

    init {
        _expenseData.value = mutableListOf()
    }

    fun addExpenseData(transaction: Transaction) {
        val currentList = _expenseData.value?.toMutableList() ?: mutableListOf()
        currentList.add(transaction)
        _expenseData.value = currentList

    }
}