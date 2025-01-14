package com.example.cc231065_tasklistapp.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.cc231065_tasklistapp.model.Task
import com.example.cc231065_tasklistapp.model.TaskViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background

@Composable
fun TaskListScreen(viewModel: TaskViewModel = viewModel()) {
    val tasks by viewModel.allTasks.collectAsState(initial = emptyList())
    val context = LocalContext.current

    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var taskCategory by remember { mutableStateOf("") }
    var taskFrequency by remember { mutableStateOf("") }

    var selectedTask by remember { mutableStateOf<Task?>(null) }
    var isDialogVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Task List", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Input fields for new task
        Text(text = "Task Title", style = MaterialTheme.typography.bodyLarge)
        BasicTextField(
            value = taskTitle,
            onValueChange = { taskTitle = it },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                // Any specific action on done can go here
            }),
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            decorationBox = { innerTextField -> Box(modifier = Modifier.fillMaxWidth()) { innerTextField() } }
        )

        Text(text = "Task Description", style = MaterialTheme.typography.bodyLarge)
        BasicTextField(
            value = taskDescription,
            onValueChange = { taskDescription = it },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            decorationBox = { innerTextField -> Box(modifier = Modifier.fillMaxWidth()) { innerTextField() } }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Input field for category (e.g., Housework, Exercise)
        Text(text = "Category", style = MaterialTheme.typography.bodyLarge)
        BasicTextField(
            value = taskCategory,
            onValueChange = { taskCategory = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            decorationBox = { innerTextField -> Box(modifier = Modifier.fillMaxWidth()) { innerTextField() } }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to add a new task
        Button(
            onClick = {
                if (taskTitle.isNotEmpty() && taskDescription.isNotEmpty() && taskCategory.isNotEmpty()) {
                    val newTask = Task(
                        title = taskTitle,
                        description = taskDescription,
                        category = taskCategory,
                        frequency = taskFrequency
                    )
                    viewModel.insertTask(newTask)
                    taskTitle = ""
                    taskDescription = ""
                    taskCategory = ""
                    Toast.makeText(context, "Task Added!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Add Task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the list of tasks
        LazyColumn {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onDeleteClick = { viewModel.deleteTask(task) },
                    onClick = {
                        selectedTask = task
                        isDialogVisible = true
                    }
                )
            }
        }
    }

    if (isDialogVisible && selectedTask != null) {
        TaskDetailsDialog(task = selectedTask!!, onDismiss = { isDialogVisible = false })
    }
}

@Composable
fun TaskItem(task: Task, onDeleteClick: () -> Unit, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        // Centering the title inside the card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize * 1.5 // Bigger title font size
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun TaskDetailsDialog(task: Task, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Task Details") },
        text = {
            Column {
                Text(
                    text = "Title: ${task.title}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Description: ${task.description}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Category: ${task.category}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Frequency: ${task.frequency}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskListScreen() {
    TaskListScreen()
}
