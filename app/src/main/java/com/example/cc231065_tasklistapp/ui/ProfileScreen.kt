package com.example.cc231065_tasklistapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.example.cc231065_tasklistapp.model.Task
import com.example.cc231065_tasklistapp.model.User


@Composable
fun ProfileScreen(user: User, tasks: List<Task>, onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(onClick = onNavigateBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Username: ${user.username}")
        Text("Level: ${user.level}")
        Text("XP: ${user.xp}")

        Spacer(modifier = Modifier.height(32.dp))

        // Display tasks with XP progress
        LazyColumn {
            val taskGroups = tasks.groupBy { it.category }.toList() // (String, List<Task>) pairs
            // Use itemsIndexed instead of items here
            itemsIndexed(taskGroups) { index, (category, tasksInCategory) ->
                TaskCategoryProgress(category, tasksInCategory, user)
            }
        }
    }
}

@Composable
fun TaskCategoryProgress(category: String, tasks: List<Task>, user: User) {
    val completedTasks = tasks.count { it.isCompleted }
    val totalTasks = tasks.size
    val totalXP = tasks.sumOf { it.xpValue }

    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = "$category Tasks: $completedTasks/$totalTasks",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "XP Progress: ${totalXP * completedTasks}/${totalXP * totalTasks}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
