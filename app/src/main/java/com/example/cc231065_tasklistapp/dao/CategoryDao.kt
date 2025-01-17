package com.example.cc231065_tasklistapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.cc231065_tasklistapp.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM category_table")
    fun getAllCategories(): Flow<List<Category>>

    // Optional: If you want just the names as a list of Strings
    @Query("SELECT name FROM category_table")
    fun getAllCategoryNames(): Flow<List<String>>
}

