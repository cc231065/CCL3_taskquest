package com.example.cc231065_tasklistapp.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cc231065_tasklistapp.model.Task
import com.example.cc231065_tasklistapp.model.TaskViewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Task List",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopCenter) // Align the title at the top-center
                .padding(top = 10.dp) // Add spacing from the top
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Input fields for new task
        Box(
            modifier = Modifier
                .align(Alignment.TopStart) // Align to the top-left
                .padding(top = 80.dp) // Position below the title
                .fillMaxWidth()
        ) {
            Column {
                Text(text = "Task Title", style = MaterialTheme.typography.bodyLarge)
                BasicTextField(
                    value = taskTitle,
                    onValueChange = { taskTitle = it },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        // Any specific action on done can go here
                    }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.medium
                        )
                        .border(
                            width = 1.dp,
                            color = Color.Black, // Black border
                            shape = MaterialTheme.shapes.medium)

                        .padding(16.dp) // Add inner padding for the text field
                )
            }
        }


        Box(
            modifier = Modifier
                .align(Alignment.TopStart) // Align to the top-left
                .padding(top = 160.dp) // Position below the Task Title
        ) {
            Column {

                Text(text = "Task Description", style = MaterialTheme.typography.bodyLarge)
                BasicTextField(
                    value = taskDescription,
                    onValueChange = { taskDescription = it },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.medium
                        )
                        .border(
                            width = 1.dp,
                            color = Color.Black, // Black border
                            shape = MaterialTheme.shapes.medium)

                        .padding(16.dp)
                )
            }
        }


        // Input field for category (e.g., Housework, Exercise)
        Box(
            modifier = Modifier
                .align(Alignment.TopStart) // Align to the top-left
                .padding(top = 240.dp) // Position below the Task Description
        ) {
            Column {

                Text(text = "Category", style = MaterialTheme.typography.bodyLarge)
                BasicTextField(
                    value = taskCategory,
                    onValueChange = { taskCategory = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.medium
                        )
                        .border(
                            width = 1.dp,
                            color = Color.Black, // Black border
                            shape = MaterialTheme.shapes.medium)
                        .padding(16.dp)
                )
            }
        }
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
                    Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT)
                        .show()
                }
            },

            // Button Colors
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red, // Background Color
                contentColor = Color.Yellow // Text Color
            ),

            modifier = Modifier
                .align(Alignment.BottomEnd) // Positions the button in the bottom-right corner
                .padding(18.dp) // Adds padding from the edges
        ) {
            Text(text = "Add Task")
        }



        Spacer(modifier = Modifier.height(16.dp))

        // Display the list of tasks
        LazyColumn(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 320.dp) // Position below the input fields
                .fillMaxWidth() // Stretch horizontally
                .fillMaxHeight(0.5f) // Take half of the remaining vertical space
        ) {
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
            .clickable { onClick() }, // Open task details when clicked
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically // Align content vertically centered
        ) {
            // Task title
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f) // Take up remaining horizontal space
            )

            // Delete icon (trashcan)
            IconButton(
                onClick = { onDeleteClick() }, // Trigger delete callback
                modifier = Modifier.padding(start = 8.dp) // Add some spacing
            ) {
                Icon(
                    imageVector = Icons.Default.Delete, // Trashcan icon
                    contentDescription = "Delete Task", // Accessibility description
                    tint = Color.Gray // Set icon color to grey
                )
            }
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
