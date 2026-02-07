package com.example.week4.model

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val dueDate: String,
    val done: Boolean = false
)