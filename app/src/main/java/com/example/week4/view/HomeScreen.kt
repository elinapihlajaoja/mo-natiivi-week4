package com.example.week4.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week4.viewmodel.TaskViewModel
import com.example.week4.model.Task
import androidx.compose.runtime.collectAsState
import com.example.week4.viewmodel.TaskFilter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    taskViewModel: TaskViewModel = viewModel(),
    onAddClick: () -> Unit = {},
    onNavigateCalendar: () -> Unit = {},
    onTaskClick: (Int) -> Unit = {},
    onNavigateSettings: () -> Unit = {}
) {
    val uiState by taskViewModel.uiState.collectAsState()
    val filteredTasks = remember(uiState.tasks, uiState.filter) {
        taskViewModel.getFilteredTasks()
    }

    val selectedTask by taskViewModel.selectedTask.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "My Tasks") },
                actions = {
                    IconButton(onClick = { taskViewModel.addTaskDialogVisible.value = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Task")
                    }
                    IconButton(onClick = onNavigateCalendar) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = "Calendar")
                    }
                    IconButton(onClick = onNavigateSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
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
            Row(
                modifier = Modifier.padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = {
                    taskViewModel.sortByDueDate()
                }, colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)) {
                    Text(text = "Sort by due date")
                }

                TaskFilter.entries.forEach { filter ->
                    FilterChip(
                        selected = uiState.filter == filter,
                        onClick = { taskViewModel.setFilter(filter) },
                        label = { Text(filter.name) }
                    )
                }
            }

            LazyColumn {
                items(items = filteredTasks, key = { it.id }) { item ->
                    TaskRow(
                        task = item,
                        onToggleDone = {
                            taskViewModel.toggleDone(item.id)
                        },
                        onClick = {
                            onTaskClick(item.id)
                        },
                        onDelete = {
                            taskViewModel.removeTask(item.id)
                        }
                    )
                }
            }
        }

        // Näytetään AddDialog, kun addTaskDialogVisible on true
        if (taskViewModel.addTaskDialogVisible.value) {
            AddDialog(viewModel = taskViewModel)
        }

        if (selectedTask != null) {
            DetailDialog(
                task = selectedTask!!,
                onClose = {
                    taskViewModel.unselectTask()
                },
                onUpdate = {
                    taskViewModel.updateTask(it)
                }
            )
        }
    }
}

@Composable
fun TaskRow(
    task: Task,
    onClick: () -> Unit,
    onToggleDone: () -> Unit,
    onDelete: () -> Unit
) {
    Card(modifier = Modifier
        .padding(8.dp)
        .clickable { onClick() }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Checkbox(
                checked = task.done,
                onCheckedChange = { onToggleDone() }
            )

            Column(modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
            ) {
                Text(text = task.title, style = MaterialTheme.typography.titleMedium)
                if (task.description.isNotBlank()) {
                    Text(text = task.description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
                Text(text = task.dueDate, style = MaterialTheme.typography.bodyMedium)
            }

            Button(onClick = onDelete,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Magenta)) {
                Text("Delete")
            }
        }
    }
}
