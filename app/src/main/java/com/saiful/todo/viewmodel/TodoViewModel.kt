package com.saiful.todo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.saiful.todo.model.Todo
import java.text.SimpleDateFormat
import java.util.*

class TodoViewModel: ViewModel() {

    var todoList = MutableLiveData<ArrayList<Todo>>()
    val todos = arrayListOf<Todo>()

    fun add(title: String) {
        val todo = Todo(title, false, getTime())
        todos.add(todo)
        sorTodoList()
    }

    private fun sorTodoList() {
        todos.sortBy { it.isCompleted }
        todoList.value = todos
    }

    fun remove(todo: Todo) {
        todos.remove(todo)
        sorTodoList()
    }

    fun update(todo: Todo) {
        val index = todos.indexOf(todo)
        val (title, isCompleted, _) = todos[index]
        todos[index] = Todo(title, !isCompleted, getTime())
        sorTodoList()
    }

    private fun getTime(): String {
        val sdf = SimpleDateFormat("dd/m/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        return currentDate.toString()
    }
}