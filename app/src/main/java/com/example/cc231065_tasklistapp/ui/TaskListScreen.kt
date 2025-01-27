    package com.example.cc231065_tasklistapp.ui

    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.items
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.lifecycle.viewmodel.compose.viewModel
    import androidx.navigation.NavController
    import androidx.navigation.compose.rememberNavController
    import androidx.compose.foundation.layout.Row
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Delete
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
    import com.example.cc231065_tasklistapp.model.Task
    import com.example.cc231065_tasklistapp.model.User
    import com.example.cc231065_tasklistapp.model.TaskViewModel
    import androidx.compose.foundation.Image
    import androidx.compose.material.icons.filled.AccountCircle
    import androidx.compose.material.icons.filled.Check
    import androidx.compose.material.icons.filled.Edit
    import androidx.compose.material.icons.filled.Refresh
    import androidx.compose.ui.res.painterResource
    import com.example.cc231065_tasklistapp.R


    @Composable
    fun TaskListScreen(navController: NavController, viewModel: TaskViewModel = viewModel(), user: User) {
        // Observe tasks from the ViewModel
        val tasks by viewModel.allTasks.collectAsState(initial = emptyList())

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
                    .align(Alignment.TopCenter)
                    .padding(top = 10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Check if the task list is empty
            if (tasks.isEmpty()) {
                Text("No tasks available", modifier = Modifier.align(Alignment.Center))
            }

            // Display the list of tasks if available
            LazyColumn(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 80.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
            ) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onDeleteClick = { viewModel.deleteTask(task) },
                        onClick = {
                            selectedTask = task
                            isDialogVisible = true
                        },
                        onCompleteClick = { viewModel.completeTask(
                            task,
                            user,
                            !task.isCompleted
                        ) },
                        onEditClick = {
                            // Navigate to the task edit screen
                            navController.navigate("taskEdit/${task.id}")
                        }
                    )
                }
            }

            IconButton(
                onClick = { navController.navigate("profile") },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = Color.Gray
                )
            }


            // Add Task Icon - Custom Icon from Drawable
            IconButton(
                onClick = { navController.navigate("taskInput") },
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Position the icon at the bottom right
                    .padding(16.dp) // Adjust padding
                    .size(56.dp) // Size of the icon
            ) {
                Image(
                    painter = painterResource(id = R.drawable.note), // Load the custom image
                    contentDescription = "Add Task",
                    modifier = Modifier.size(36.dp) // Adjust icon size
                )
            }
        }

        // Show the task details dialog when selected
        if (isDialogVisible && selectedTask != null) {
            TaskDetailsDialog(task = selectedTask!!, onDismiss = { isDialogVisible = false })
        }
    }

    @Composable
    fun TaskItem(
        task: Task,
        onDeleteClick: () -> Unit,
        onClick: () -> Unit,
        onCompleteClick: (Boolean) -> Unit,
        onEditClick: () -> Unit // Add this parameter
    ) {
        // Define colors for each category
        val categoryColors = mapOf(
            "Onetime" to Color(0xFFB0BEC5), // Light Gray
            "Daily" to Color(0xFFBBDEFB),   // Soft Light Blue
            "Weekly" to Color(0xFF42A5F5),  // Vibrant Medium Blue
            "Monthly" to Color(0xFF1565C0)  // Deep Dark Blue
        )

        // Assign a default color if the category is not mapped
        val backgroundColor = if (task.isCompleted) {
            Color(0xFF02d62d) // Green completion color for completed tasks
        } else {
            categoryColors[task.category] ?: MaterialTheme.colorScheme.surfaceVariant
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp)
                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp) // Adjust the height of each task item
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }

                // Icons for actions - placed on the right side with a slight left position for edit
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    // Edit Button (slightly left of the delete icon)
                    IconButton(
                        onClick = { onEditClick() },
                        modifier = Modifier.align(Alignment.TopEnd).padding(end = 36.dp) // Adjust this padding to position the edit button
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Task")
                    }

                    // Delete Button (on the right side)
                    IconButton(
                        onClick = { onDeleteClick() },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Task")
                    }
                }

                Row(
                    modifier = Modifier.align(Alignment.BottomStart)
                ) {
                    Text(
                        text = task.category,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = "- ${task.xpValue} XP")
                }

                IconButton(
                    onClick = { onCompleteClick(!task.isCompleted) },
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    if (task.isCompleted) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reset Task")
                    } else {
                        Icon(imageVector = Icons.Default.Check, contentDescription = "Mark as Complete")
                    }
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
        TaskListScreen(
            navController = rememberNavController(),
            user = User(1, "PreviewUser", 50, 1) // Dummy data for preview
        )
    }
