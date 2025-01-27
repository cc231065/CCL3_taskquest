package com.example.cc231065_tasklistapp.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cc231065_tasklistapp.database.TaskDatabaseInstance
import com.example.cc231065_tasklistapp.dao.TaskDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDao: TaskDao = TaskDatabaseInstance.getDatabase(application).taskDao()

    // StateFlow for the selected task
    private val _taskState = MutableStateFlow<Task?>(null)
    val taskState: StateFlow<Task?> = _taskState

    // Flow to observe all tasks
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

    init {
        // Add logging to ensure tasks are fetched
        viewModelScope.launch {
            allTasks.collect { tasks ->
                println("All Tasks: $tasks") // Debugging log
                Log.d("TaskViewModel", "All Tasks: ${tasks.map { it.id }}")
            }
        }
    }

    // Fetch task by ID and emit it to the StateFlow
    fun fetchTaskById(taskId: Int) {
        viewModelScope.launch {
            Log.d("TaskViewModel", "Fetching task for ID: $taskId")
            allTasks.collect { tasks ->
                val task = tasks.find { it.id == taskId }
                Log.d("TaskViewModel", "Found task: $task")
                _taskState.value = task
            }
        }
    }


    // Add an update function if not already there
    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskDao.updateTask(task)
            Log.d("TaskViewModel", "Task updated: $task")
        }
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

    fun completeTask(task: Task, user: User, isCompleted: Boolean) {
        task.isCompleted = isCompleted
        task.completedDate = if (isCompleted) Date() else null
        user.xp += task.xpValue // Award XP to the user based on task completion
        updateUserLevel(user) // Update user level based on total XP
        viewModelScope.launch {
            taskDao.updateTask(task)
        }
        println("Task '${task.title}' completion toggled: $isCompleted")
    }
}