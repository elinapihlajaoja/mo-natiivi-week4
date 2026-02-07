package com.example.week4.viewmodel

import com.example.week4.model.Task

data class TaskUIState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val filter: TaskFilter= TaskFilter.ALL,
)

enum class TaskFilter {
    ALL, COMPLETED
}
