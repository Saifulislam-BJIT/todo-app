package com.saiful.todo.model

data class Todo (
    val title: String,
    val isCompleted: Boolean,
    var modifyTime: String
)