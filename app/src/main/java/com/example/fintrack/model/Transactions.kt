package com.example.fintrack.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Expense")
@Parcelize
data class Transactions(
    val title: String,
    val category: String,
    val amount: String,
    val date: String,
    val image: String
) : Parcelable