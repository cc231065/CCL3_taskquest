package com.example.cc231065_tasklistapp.database

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cc231065_tasklistapp.model.Converters

object TaskDatabaseInstance {

    @Volatile
    private var INSTANCE: TaskDatabase? = null

    // Migration from version 4 to version 5 (adding category_table)
    val MIGRATION_5_6 = object : Migration(5, 6) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Add new columns for xpValue and completedDate in tasks table
            database.execSQL("ALTER TABLE tasks ADD COLUMN xpValue INTEGER DEFAULT 0 NOT NULL")
            database.execSQL("ALTER TABLE tasks ADD COLUMN completedDate INTEGER") // For Date, stored as timestamp (Long)

            // You can also create new tables if needed (for User model or any other)

            // Example: If you need to create a User table (optional)
            // database.execSQL("""
            // CREATE TABLE IF NOT EXISTS `user_table` (
            //     `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            //     `username` TEXT NOT NULL,
            //     `xp` INTEGER NOT NULL DEFAULT 0,
            //     `level` INTEGER NOT NULL DEFAULT 1
            // )
            // """.trimIndent())
        }
    }



    fun getDatabase(context: Context): TaskDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "task_database"
            )
                .addMigrations(MIGRATION_5_6) // Add the migration from version 3 to version 4
                .build()
            INSTANCE = instance
            println("TaskDatabase initialized")
            instance
        }
    }
}
