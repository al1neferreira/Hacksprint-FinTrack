package com.example.fintrack.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fintrack.model.ColorTransactionConverter
import com.example.fintrack.model.Transaction

@Database(entities = [Transaction::class], version = 1, exportSchema = false)
@TypeConverters(ColorTransactionConverter::class)
abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun getExpenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var instance: ExpenseDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) =
            instance ?: synchronized(LOCK) {
                createDatabase(context).also {
                    instance = it
                }
            }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ExpenseDatabase::class.java,
                "expense_db"
            ).build()
    }

}




