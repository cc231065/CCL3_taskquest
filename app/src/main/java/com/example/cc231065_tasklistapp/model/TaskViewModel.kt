package com.example.cc231065_tasklistapp.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cc231065_tasklistapp.database.TaskDatabaseInstance
import com.example.cc231065_tasklistapp.dao.TaskDao
import com.example.cc231065_tasklistapp.dao.CategoryDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val taskDao: TaskDao = TaskDatabaseInstance.getDatabase(application).taskDao()
    private val categoryDao: CategoryDao = TaskDatabaseInstance.getDatabase(application).categoryDao()

    // Flow to observe all tasks
    val allTasks: Flow<List<Task>> = taskDao.getAllTasks()

    // Flow to observe tasks by category
    fun getTasksByCategory(category: String): Flow<List<Task>> {
        return taskDao.getTasksByCategory(category)
    }

    // Flow to observe all categories (only category names as String list)
    val allCategories: Flow<List<String>> = categoryDao.getAllCategoryNames()

    // Function to insert a task into the database
    fun insertTask(task: Task) {
        viewModelScope.launch {
            taskDao.insertTask(task)
        }
    }

    // Function to update an existing task
    fun updateTask(task: Task) {
        viewModelScope.launch {
            taskDao.updateTask(task)
        }
    }

    // Function to delete a task from the database
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }

    // Function to insert a new category into the database
    fun addCategory(category: String) {
        viewModelScope.launch {
            categoryDao.insertCategory(Category(name = category))
        }
    }

    // Function to delete a category from the database
    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            categoryDao.deleteCategory(category)
        }
    }
}
