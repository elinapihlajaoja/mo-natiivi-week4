package com.example.week4.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week4.model.Task
import com.example.week4.model.mockTasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TaskUIState())
    val uiState: StateFlow<TaskUIState> = _uiState.asStateFlow()

    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask

    val addTaskDialogVisible = mutableStateOf<Boolean>(false)


    init {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                _uiState.value = _uiState.value.copy(tasks = mockTasks, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false, errorMessage = e.message
                )
            }
        }
    }

    fun openTask(id: Int) {
        val task = _uiState.value.tasks.find { it.id == id }
        _selectedTask.value = task
    }

    fun toggleDone(id: Int) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.map {
                if (it.id == id) it.copy(done = !it.done)
                else it
            }
        )
    }

    fun updateTask(update: Task) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.map {
                if (it.id == update.id) update
                else it
            },
        )
        _selectedTask.value = null
    }

    fun removeTask(id: Int) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.filter { it.id != id }
        )
    }

    fun addTask(title: String, description: String, dueDate: String) {
        val nextId = (_uiState.value.tasks.maxOfOrNull { it.id } ?: 0) + 1
        val newTask = Task(
            id = nextId,
            title = title,
            description = description,
            dueDate = dueDate,
            done = false
        )
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks + newTask
        )
        addTaskDialogVisible.value = false
    }

    fun setFilter(filter: TaskFilter) {
        _uiState.value = _uiState.value.copy(filter = filter)
    }

    fun getFilteredTasks(): List<Task> {
        return when (_uiState.value.filter) {
            TaskFilter.ALL -> _uiState.value.tasks
            TaskFilter.COMPLETED -> _uiState.value.tasks.filter { it.done }
        }
    }

    fun sortByDueDate() {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.sortedBy { it.dueDate }
        )
    }

    fun selectTask(task: Task) {
        _selectedTask.value = task
    }

    fun unselectTask() {
        _selectedTask.value = null
    }
}
