package com.example.cc231065_tasklistapp.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cc231065_tasklistapp.database.TaskDatabaseInstance
import com.example.cc231065_tasklistapp.dao.TaskDao
import com.example.cc231065_tasklistapp.dao.CategoryDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.Calendar

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDao: TaskDao = TaskDatabaseInstance.getDatabase(application).taskDao()
    private val categoryDao: CategoryDao = TaskDatabaseInstance.getDatabase(application).categoryDao()

    // Flow to observe all tasks
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()


    init {
        // Add logging to ensure tasks are fetched
        viewModelScope.launch {
            allTasks.collect { tasks ->
                println("All Tasks: $tasks") // Debugging log
            }
        }
    }

    // Flow to observe tasks by category
    fun getTasksByCategory(category: String): Flow<List<Task>> {
        return taskDao.getTasksByCategory(category)
    }

    // Function to insert a task into the database
    fun insertTask(task: Task) {
        viewModelScope.launch {
            taskDao.insertTask(task)
            println("Task inserted: ${task.title}") // Debugging log
        }
    }

    // Function to delete a task from the database
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
            println("Task deleted: ${task.title}") // Debugging log
        }
    }

    fun updateTaskXP(task: Task) {
        task.xpValue = when (task.category) {
            "Daily" -> 50
            "Weekly" -> 100
            "Monthly" -> 200
            "Onetime" -> 100
            else -> 0
        }
    }

    fun resetTask(task: Task, currentDate: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val truncatedCurrentDate = calendar.time

        when (task.category) {
            "Daily" -> {
                // If the task is completed and it's a new day, reset the completion status.
                if (task.isCompleted && task.completedDate?.before(truncatedCurrentDate) == true) {
                    task.isCompleted = false
                }
            }
            "Weekly" -> {
                // Reset the weekly task after a week if completed last week
                calendar.add(Calendar.WEEK_OF_YEAR, -1)
                val previousWeekDate = calendar.time
                if (task.isCompleted && task.completedDate?.before(previousWeekDate) == true) {
                    task.isCompleted = false
                }
            }
            "Monthly" -> {
                // Reset the monthly task after a month if completed last month
                calendar.add(Calendar.MONTH, -1)
                val previousMonthDate = calendar.time
                if (task.isCompleted && task.completedDate?.before(previousMonthDate) == true) {
                    task.isCompleted = false
                }
            }
            "Onetime" -> {
                // No reset needed for onetime tasks
            }
        }
    }

    fun completeTask(task: Task, user: User) {
        task.isCompleted = true
        task.completedDate = Date()
        user.xp += task.xpValue // Award XP to the user based on task completion

        // You can also mark the task as completed in the database or ViewModel.
        updateUserLevel(user) // Update user level based on total XP
    }

}
