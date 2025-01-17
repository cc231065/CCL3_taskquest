package com.example.cc231065_tasklistapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String
)
