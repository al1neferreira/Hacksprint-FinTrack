package com.example.fintrack.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fintrack.model.Transactions

class HomeViewModel : ViewModel() {
    private val _expenseData = MutableLiveData<List<Transactions>>()
    val expenseData: LiveData<List<Transactions>> get() = _expenseData

    init {
        _expenseData.value = mutableListOf()
    }

    fun addExpenseData(transaction: Transactions) {
        val currentList = _expenseData.value?.toMutableList() ?: mutableListOf()
        currentList.add(transaction)
        _expenseData.value = currentList

    }
}