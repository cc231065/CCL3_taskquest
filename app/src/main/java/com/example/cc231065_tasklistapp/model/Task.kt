package com.example.cc231065_tasklistapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val category: String,
    var xpValue: Int = 0,
    var isCompleted: Boolean = false,
    @TypeConverters(Converters::class) var completedDate: Date? = null
)
