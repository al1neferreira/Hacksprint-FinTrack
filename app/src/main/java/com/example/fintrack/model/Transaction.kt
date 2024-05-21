package com.example.fintrack.model

data class Transaction(
    val title: String,
    val category: String,
    val amount: String,
    val date: String,
    val colorTransaction: ColorTransaction,
    val image: String
) {
}