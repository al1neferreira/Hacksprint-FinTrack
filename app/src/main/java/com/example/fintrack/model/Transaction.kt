package com.example.fintrack.model

import androidx.room.Entity

@Entity
data class Transactions(
    val title: String,
    val category: String,
    val amount: String,
    val date: String,
    val colorTransaction: ColorTransaction? = null,
    val image: String
) {
}