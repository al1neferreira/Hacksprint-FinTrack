package com.example.fintrack.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "all_transactions")

data class Transaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("category")
    val category: String,

    @ColumnInfo("amount")
    val amount: String,

    @ColumnInfo("date")
    val date: String,

    @ColumnInfo("color")
    val colorTransaction: ColorTransaction,

    @ColumnInfo("image")
    val image: String
): Serializable