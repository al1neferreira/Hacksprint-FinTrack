package com.example.fintrack.db

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Category")

data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo("key")
    val name: String,
    @ColumnInfo("is_selected")
    val isSelected: Boolean
)
