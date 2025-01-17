package com.example.cc231065_tasklistapp.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import com.example.cc231065_tasklistapp.model.Task
import com.example.cc231065_tasklistapp.model.TaskViewModel

@Composable
fun TaskListScreen(navController: NavController, viewModel: TaskViewModel = viewModel()) {
    val tasks by viewModel.allTasks.collectAsState(initial = emptyList())
    val context = LocalContext.current

    var selectedTask by remember { mutableStateOf<Task?>(null) }
    var isDialogVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Title
        Text(
            text = "Task List",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 10.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Add Task Button
        Button(
            onClick = { navController.navigate("taskInput") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(18.dp)
        ) {
            Text(text = "Add Task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Task List
        LazyColumn(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 80.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
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

    // Task Details Dialog
    if (isDialogVisible && selectedTask != null) {
        TaskDetailsDialog(task = selectedTask!!, onDismiss = { isDialogVisible = false })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskInputScreentest(navController: NavController, viewModel: TaskViewModel = viewModel()) {
    val context = LocalContext.current

    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var taskCategory by remember { mutableStateOf("") }
    var taskFrequency by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Task") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Task Title Input
            Text(text = "Task Title", style = MaterialTheme.typography.bodyLarge)
            BasicTextField(
                value = taskTitle,
                onValueChange = { taskTitle = it },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
                    .border(1.dp, Color.Black, MaterialTheme.shapes.medium)
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Task Description Input
            Text(text = "Task Description", style = MaterialTheme.typography.bodyLarge)
            BasicTextField(
                value = taskDescription,
                onValueChange = { taskDescription = it },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
                    .border(1.dp, Color.Black, MaterialTheme.shapes.medium)
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Task Category Input
            Text(text = "Task Category", style = MaterialTheme.typography.bodyLarge)
            BasicTextField(
                value = taskCategory,
                onValueChange = { taskCategory = it },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
                    .border(1.dp, Color.Black, MaterialTheme.shapes.medium)
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Task Frequency Input
            Text(text = "Task Frequency", style = MaterialTheme.typography.bodyLarge)
            BasicTextField(
                value = taskFrequency,
                onValueChange = { taskFrequency = it },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
                    .border(1.dp, Color.Black, MaterialTheme.shapes.medium)
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Save Task Button
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
                        Toast.makeText(context, "Task Added!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    } else {
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Save Task")
            }
        }
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { onDeleteClick() }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Task",
                    tint = Color.Gray
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
                Text(text = "Title: ${task.title}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Description: ${task.description}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Category: ${task.category}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Frequency: ${task.frequency}", style = MaterialTheme.typography.bodyMedium)
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
    TaskListScreen(navController = rememberNavController())
}
