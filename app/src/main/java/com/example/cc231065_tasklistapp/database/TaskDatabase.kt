package com.example.cc231065_tasklistapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cc231065_tasklistapp.dao.CategoryDao
import com.example.cc231065_tasklistapp.dao.TaskDao
import com.example.cc231065_tasklistapp.model.Task
import com.example.cc231065_tasklistapp.model.Category
import com.example.cc231065_tasklistapp.model.Converters

@Database(entities = [Task::class, Category::class], version = 6, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao
}
