package com.example.cc231065_tasklistapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cc231065_tasklistapp.dao.CategoryDao
import com.example.cc231065_tasklistapp.dao.TaskDao
import com.example.cc231065_tasklistapp.model.Task
import com.example.cc231065_tasklistapp.model.Category

@Database(entities = [Task::class, Category::class], version = 4, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao
}
