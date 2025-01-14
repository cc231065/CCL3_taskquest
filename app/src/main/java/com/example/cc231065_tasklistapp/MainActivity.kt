package com.example.cc231065_tasklistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.cc231065_tasklistapp.ui.TaskListScreen
import com.example.cc231065_tasklistapp.ui.theme.TaskListAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskListAppTheme {
                // This is where the Task List screen is set as the main screen
                Surface(color = MaterialTheme.colorScheme.background) {
                    TaskListScreen() // This displays the TaskListScreen composable
                }
            }
        }
    }
}
