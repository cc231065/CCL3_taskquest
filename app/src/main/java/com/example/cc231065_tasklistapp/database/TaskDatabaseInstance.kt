package com.example.cc231065_tasklistapp.database

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cc231065_tasklistapp.model.Category

object TaskDatabaseInstance {

    @Volatile
    private var INSTANCE: TaskDatabase? = null

    // Migration from version 3 to version 4 (adding category_table)
    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // SQL to create the category_table
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS `category_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL)"
            )
        }
    }

    fun getDatabase(context: Context): TaskDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "task_database"
            )
                .addMigrations(MIGRATION_3_4) // Add the migration from version 3 to version 4
                .build()
            INSTANCE = instance
            instance
        }
    }
}
