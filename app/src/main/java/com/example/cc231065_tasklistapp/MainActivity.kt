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
import com.example.cc231065_tasklistapp.model.Task
import com.example.cc231065_tasklistapp.model.User
import com.example.cc231065_tasklistapp.ui.TaskInputScreen
import com.example.cc231065_tasklistapp.ui.TaskListScreen
import com.example.cc231065_tasklistapp.ui.ProfileScreen
import com.example.cc231065_tasklistapp.model.TaskViewModel
import com.example.cc231065_tasklistapp.model.TaskViewModelFactory
import com.example.cc231065_tasklistapp.ui.theme.TaskListAppTheme
import com.example.cc231065_tasklistapp.ui.TaskEditScreen


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
                                viewModel = taskViewModel, // Passing the ViewModel here
                                user = user
                            )
                        }

                        // This route will handle editing a task by passing the taskId
                        composable("taskEdit/{taskId}") { backStackEntry ->
                            val taskId = backStackEntry.arguments?.getString("taskId")
                            TaskEditScreen(
                                navController = navController,
                                taskId = taskId,
                                taskViewModel = taskViewModel
                            )
                        }

                        composable("taskInput") {
                            TaskInputScreen(
                                navController = navController,
                                onTaskAdded = { title, description, category ->
                                    // Calculate XP based on the category
                                    val xp = when (category) {
                                        "Daily" -> 50
                                        "Weekly" -> 100
                                        "Monthly" -> 200
                                        "Onetime" -> 100
                                        else -> 0
                                    }

                                    // Add the task using the ViewModel, including the calculated XP
                                    taskViewModel.insertTask(
                                        Task(
                                            title = title,
                                            description = description,
                                            category = category,
                                            xpValue = xp // Add the XP to the task object
                                        )
                                    )

                                    // Navigate back to the task list after adding the task
                                    navController.popBackStack()
                                }
                            )
                        }


                        // New Profile Screen destination
                        composable("profile") {
                            // Collect tasks from ViewModel
                            val tasks = taskViewModel.allTasks.collectAsState(initial = emptyList()).value

                            ProfileScreen(
                                user = User(1, "TestUser", 100, 1), // Replace with your user data
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
