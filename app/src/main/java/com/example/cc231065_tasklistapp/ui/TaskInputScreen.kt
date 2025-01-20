package com.example.cc231065_tasklistapp.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.IconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskInputScreen(
    navController: NavController,
    onTaskAdded: (String, String, String) -> Unit
) {
    val context = LocalContext.current

    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var showCategoryDialog by remember { mutableStateOf(false) }

    // Corrected categories
    val categories = listOf("Daily", "Weekly", "Monthly", "One Time")

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
                },
                actions = {
                    TextButton(
                        onClick = {
                            if (taskTitle.isNotBlank() && taskDescription.isNotBlank()) {
                                showCategoryDialog = true
                            } else {
                                Toast.makeText(context, "Please fill in Task Title and Description", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Text("Done")
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
            // Task Title Field
            Text("Task Title", style = MaterialTheme.typography.bodyLarge)
            BasicTextField(
                value = taskTitle,
                onValueChange = { taskTitle = it },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {}),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Task Description Field
            Text("Task Description", style = MaterialTheme.typography.bodyLarge)
            BasicTextField(
                value = taskDescription,
                onValueChange = { taskDescription = it },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surface,
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(16.dp)
            )
        }
    }

    // Category Selection Dialog
    if (showCategoryDialog) {
        AlertDialog(
            onDismissRequest = { showCategoryDialog = false },
            title = { Text("Select Category") },
            text = {
                Column {
                    Text("Select a Category:")
                    Spacer(modifier = Modifier.height(8.dp))
                    categories.forEach { category ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedCategory = category }
                                .padding(8.dp)
                        ) {
                            RadioButton(
                                selected = selectedCategory == category,
                                onClick = { selectedCategory = category }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(category)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (selectedCategory != null) {
                            // Add task and navigate back
                            onTaskAdded(taskTitle, taskDescription, selectedCategory!!)
                        } else {
                            Toast.makeText(context, "Please select a category", Toast.LENGTH_SHORT).show()
                        }
                        navController.navigate("taskList") {
                            popUpTo("taskInput") { inclusive = true } // This will clear the back stack up to the TaskInput screen
                        }
                    }
                ) {
                    Text("Select Category")
                }
            },
            dismissButton = {
                Button(onClick = { showCategoryDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
