    package com.example.cc231065_tasklistapp.ui

    import android.app.Application
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
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Add
    import androidx.compose.material.icons.filled.ArrowBack
    import androidx.compose.material.icons.filled.Delete
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
    import com.example.cc231065_tasklistapp.model.Task
    import com.example.cc231065_tasklistapp.model.User
    import com.example.cc231065_tasklistapp.model.TaskViewModel
    import com.example.cc231065_tasklistapp.ui.TaskInputScreen
    import androidx.compose.foundation.Image
    import androidx.compose.material.icons.filled.AccountCircle
    import androidx.compose.material.icons.filled.Check
    import androidx.compose.ui.res.painterResource
    import com.example.cc231065_tasklistapp.R
    import com.example.cc231065_tasklistapp.model.TaskViewModelFactory


    @Composable
    fun TaskListScreen(navController: NavController, viewModel: TaskViewModel = viewModel(), user: User) {
        // Observe tasks from the ViewModel
        val tasks by viewModel.allTasks.collectAsState(initial = emptyList())
        val context = LocalContext.current

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
                            user
                        ) }
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
        onCompleteClick: () -> Unit // New lambda for the completion button
    ) {
        // Define colors for each category
        val categoryColors = mapOf(
            "Onetime" to Color(0xFFB0BEC5), // Light Gray
            "Daily" to Color(0xFFBBDEFB),   // Soft Light Blue
            "Weekly" to Color(0xFF42A5F5),  // Vibrant Medium Blue
            "Monthly" to Color(0xFF1565C0)  // Deep Dark Blue
        )

        // Assign a default color if the category is not mapped
        val backgroundColor = categoryColors[task.category] ?: MaterialTheme.colorScheme.surfaceVariant

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp)
                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            // Wrap everything in a Box to allow flexible alignment
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp) // Adjust the height of each task item
                    .padding(16.dp)
            ) {
                // Task title and description (aligned to top-left)
                Column {
                    // Task title
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.align(Alignment.Start) // Align text to the start (top-left)
                    )

                    Spacer(modifier = Modifier.height(8.dp)) // Add spacing between title and body

                    // Task body (underneath title)
                    Text(
                        text = task.description ?: "No description provided", // Fallback text if description is null
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.Start) // Align text to the start (under title)
                    )
                }

                // Delete icon (aligned to top-right)
                IconButton(
                    onClick = { onDeleteClick() },
                    modifier = Modifier
                        .align(Alignment.TopEnd) // Position the icon at the top-right
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Task",
                        tint = Color.Black
                    )
                }

                // Category and XP (aligned to bottom-left)
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart) // Align to the bottom-left corner
                ) {
                    // Display category
                    Text(
                        text = task.category,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.Black, // Adjust text color for better readability
                        modifier = Modifier.padding(end = 8.dp) // Add spacing between category and XP
                    )

                    // Display XP value
                    Text(
                        text = "- ${task.xpValue} XP", // XP text with a "+" sign
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black // Adjust text color for better readability
                    )
                }

                // Completion icon (aligned to bottom-right)
                IconButton(
                    onClick = { onCompleteClick() }, // Handle task completion
                    modifier = Modifier
                        .align(Alignment.BottomEnd) // Align to the bottom-right corner
                ) {
                    Icon(
                        imageVector = Icons.Default.Check, // Checkmark icon
                        contentDescription = "Mark as Complete",
                        tint = Color.Black
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
