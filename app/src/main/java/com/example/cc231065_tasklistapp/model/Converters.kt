package com.example.cc231065_tasklistapp.model

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    // Convert Date to Long (timestamp)
    @TypeConverter
    fun fromDateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    // Convert Long (timestamp) to Date
    @TypeConverter
    fun fromTimestampToDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }
}