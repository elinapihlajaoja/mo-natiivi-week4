package com.example.week4.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import com.example.week4.viewmodel.TaskViewModel

@Composable
fun AddDialog(viewModel: TaskViewModel) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }

    if (viewModel.addTaskDialogVisible.value) {
        AlertDialog(
            onDismissRequest = { viewModel.addTaskDialogVisible.value = false },
            title = { Text("Add a new task") },
            text = {
                Column {
                    TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                    TextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
                    TextField(value = dueDate, onValueChange = { dueDate = it }, label = { Text("Due Date") })
                }
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.addTask(title, description, dueDate)
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = { viewModel.addTaskDialogVisible.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
