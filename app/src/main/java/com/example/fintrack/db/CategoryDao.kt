package com.example.fintrack.db

import androidx.room.Dao
import androidx.room.DatabaseView
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getAll():List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categoryEntity: List<CategoryEntity> )

}