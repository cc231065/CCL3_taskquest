package com.example.cc231065_tasklistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.cc231065_tasklistapp.ui.TaskInputScreen
import com.example.cc231065_tasklistapp.ui.TaskListScreen
import com.example.cc231065_tasklistapp.ui.theme.TaskListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskListAppTheme {
                val navController = rememberNavController()

                // Sample category list (this should be fetched from your database)
                val categories = listOf("Work", "Personal", "Shopping")

                // Function to handle when a task is added
                val onTaskAdded: (String, String, String) -> Unit = { title, description, category ->
                    // Handle the logic of adding a task here
                    println("Task added: $title, $description, $category")
                }

                // Function to handle when a new category is added
                val onCategoryAdded: (String) -> Unit = { category ->
                    // Handle the logic of adding a new category here
                    println("Category added: $category")
                }

                // Set up navigation with NavHost
                Surface(color = MaterialTheme.colorScheme.background) {
                    NavHost(
                        navController = navController,
                        startDestination = "taskList"
                    ) {
                        // Route for TaskListScreen
                        composable("taskList") {
                            TaskListScreen(navController = navController)
                        }

                        // Route for TaskInputScreen
                        composable("taskInput") {
                            TaskInputScreen(
                                navController = navController,
                                onTaskAdded = onTaskAdded,
                                categories = categories,
                                onCategoryAdded = onCategoryAdded
                            )
                        }
                    }
                }
            }
        }
    }
}
