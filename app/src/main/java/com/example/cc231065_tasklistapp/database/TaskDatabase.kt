package com.example.cc231065_tasklistapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cc231065_tasklistapp.dao.TaskDao
import com.example.cc231065_tasklistapp.model.Task

@Database(entities = [Task::class], version = 2, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
