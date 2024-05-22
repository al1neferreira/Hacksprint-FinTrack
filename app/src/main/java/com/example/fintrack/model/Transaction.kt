package com.example.fintrack.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
    (
    tableName = "EXPENSE",
//    foreignKeys = [
//        ForeignKey(
//            entity = ColorTransaction::class,
//            parentColumns = ["name"],
//            childColumns = ["colorTransaction"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
)

data class Transaction(
    @PrimaryKey
    val title: String,
    val category: String,
    val amount: String,
    val date: String,
    val colorTransaction: ColorTransaction,
    val image: String
)