package com.example.cc231065_tasklistapp.ui

import android.app.Application
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cc231065_tasklistapp.model.Task
import com.example.cc231065_tasklistapp.model.TaskViewModel
import com.example.cc231065_tasklistapp.model.TaskViewModelFactory

@Composable
fun TaskEditScreen(
    navController: NavController,
    taskId: String?,
    taskViewModel: TaskViewModel
) {
    val taskIdInt = taskId?.toIntOrNull()
    val task by taskViewModel.taskState.collectAsState()

    // Fetch the task when the screen loads
    LaunchedEffect(taskIdInt) {
        if (taskIdInt != null) {
            Log.d("TaskEditScreen", "Fetching task for ID: $taskIdInt")
            taskViewModel.fetchTaskById(taskIdInt)
        }
    }

    // Editable states for task properties
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var xpValue by remember { mutableStateOf(0) }

    // Update the local state when the task changes
    LaunchedEffect(task) {
        task?.let {
            title = it.title
            description = it.description
            category = it.category
            xpValue = it.xpValue
        }
    }








    // UI for editing the task
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Edit Task", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Task Title Label and input field
        Text(text = "Task Title", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        BasicTextField(
            value = title,
            onValueChange = {
                title = it
                Log.d("TaskEditScreen", "Title updated: $it")
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.LightGray)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Task Description Label and input field
        Text(text = "Task Description", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
        BasicTextField(
            value = description,
            onValueChange = {
                description = it
                Log.d("TaskEditScreen", "Description updated: $it")
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.LightGray)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Save button
        Button(
            onClick = {
                // Log the task data being saved
                Log.d("TaskEditScreen", "Saving task: Title=$title, Description=$description, Category=$category, XP=$xpValue")

                // Save the updated task with only title and description changed
                taskId?.let { taskIdStr ->
                    taskViewModel.updateTask(
                        Task(
                            id = taskIdStr.toInt(),
                            title = title,
                            description = description,
                            category = category, // Keep the category unchanged
                            xpValue = xpValue // Keep the xpValue unchanged
                        )
                    )
                }
                navController.popBackStack() // Go back after saving
            }
        ) {
            Text("Save Task")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Cancel button to go back
        Button(onClick = { navController.popBackStack() }) {
            Text("Cancel")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskEditScreen() {
    // Use LocalContext and mock ViewModel creation in Preview
    val mockViewModel = viewModel<TaskViewModel>(factory = TaskViewModelFactory(Application()))

    TaskEditScreen(
        navController = rememberNavController(),
        taskId = "1",
        taskViewModel = mockViewModel
    )
}
