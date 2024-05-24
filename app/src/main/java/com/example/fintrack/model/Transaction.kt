package com.example.fintrack.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "EXPENSE")

data class Transaction(
    @PrimaryKey
    val title: String,
    val category: String,
    val amount: String,
    val date: String,
    val colorTransaction: ColorTransaction,
    val image: String,
    val transactionId: Int
): Serializable