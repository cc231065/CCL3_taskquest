package com.example.cc231065_tasklistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cc231065_tasklistapp.model.Task
import com.example.cc231065_tasklistapp.model.User
import com.example.cc231065_tasklistapp.ui.TaskInputScreen
import com.example.cc231065_tasklistapp.ui.TaskListScreen
import com.example.cc231065_tasklistapp.ui.ProfileScreen
import com.example.cc231065_tasklistapp.model.TaskViewModel
import com.example.cc231065_tasklistapp.model.TaskViewModelFactory
import com.example.cc231065_tasklistapp.ui.theme.TaskListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskListAppTheme {
                val navController = rememberNavController()

                // Accessing TaskViewModel using the viewModel() delegate
                val taskViewModel: TaskViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
                    factory = TaskViewModelFactory(application)
                )

                // Example user (you can fetch this from the database or pass from another source)
                val user = User(id = 1, username = "JohnDoe", xp = 100, level = 2)

                Surface(color = MaterialTheme.colorScheme.background) {
                    NavHost(
                        navController = navController,
                        startDestination = "taskList"
                    ) {
                        composable("taskList") {
                            TaskListScreen(
                                navController = navController,
                                viewModel = taskViewModel // Passing the ViewModel here
                            )
                        }

                        composable("taskInput") {
                            TaskInputScreen(
                                navController = navController,
                                onTaskAdded = { title, description, category ->
                                    // Call ViewModel to insert the task
                                    taskViewModel.insertTask(Task(title = title, description = description, category = category))
                                    // Navigate back after adding the task
                                    navController.popBackStack()
                                }
                            )
                        }

                        // New Profile Screen destination
                        composable("profile") {
                            // Collect tasks from ViewModel
                            val tasks = taskViewModel.allTasks.collectAsState(initial = emptyList()).value

                            ProfileScreen(
                                user = User(1, "TestUser", 500, 2), // Replace with your user data
                                tasks = tasks,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }

                    }
                }
            }
        }
    }
}
