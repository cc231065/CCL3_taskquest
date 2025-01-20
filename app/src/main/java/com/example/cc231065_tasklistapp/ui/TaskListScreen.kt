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
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Add
    import androidx.compose.material.icons.filled.ArrowBack
    import androidx.compose.material.icons.filled.Delete
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
    import com.example.cc231065_tasklistapp.model.Task
    import com.example.cc231065_tasklistapp.model.TaskViewModel
    import com.example.cc231065_tasklistapp.ui.TaskInputScreen
    import androidx.compose.foundation.Image
    import androidx.compose.material.icons.filled.AccountCircle
    import androidx.compose.ui.res.painterResource
    import com.example.cc231065_tasklistapp.R



    @Composable
    fun TaskListScreen(navController: NavController, viewModel: TaskViewModel = viewModel()) {
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp) // Adjust the height of each task item
                    .padding(16.dp)
            ) {
                // Center the task title
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .align(Alignment.Center) // Center the text horizontally and vertically
                )

                // Delete icon at the top right
                IconButton(
                    onClick = { onDeleteClick() },
                    modifier = Modifier
                        .align(Alignment.TopEnd) // Position the icon at the top right
                        .padding(2.dp) // Optional padding around the icon
                ) {
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
