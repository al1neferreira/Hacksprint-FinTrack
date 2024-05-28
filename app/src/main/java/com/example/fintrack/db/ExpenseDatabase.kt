package com.example.fintrack.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fintrack.model.ColorTransactionConverter
import com.example.fintrack.model.Transaction

@Database(
    entities = [Transaction::class, CategoryEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(ColorTransactionConverter::class)

abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun getExpenseDao(): ExpenseDao
    abstract fun getCategoryDao():CategoryDao

}




