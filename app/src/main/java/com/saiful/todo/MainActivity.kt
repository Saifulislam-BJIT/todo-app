package com.saiful.todo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saiful.todo.adapter.TodoRecyclerAdapter
import com.saiful.todo.viewmodel.TodoViewModel
import com.saiful.todo.viewmodel.TodoViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private var layoutManager = LinearLayoutManager(this)
    private lateinit var viewModel: TodoViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var addTask: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler)
        val factory = TodoViewModelProviderFactory()
        viewModel = ViewModelProvider(this, factory)[TodoViewModel::class.java]

        addTask = findViewById(R.id.add_task)
        addTask.setOnClickListener {
            createTask()
        }

        initializeAdapter()
    }

    private fun initializeAdapter() {
        recyclerView.layoutManager = layoutManager
        observeData()
    }

    private fun observeData() {
        viewModel.todoList.observe(this, Observer {
            Log.i("data", it.toString())
            recyclerView.adapter = TodoRecyclerAdapter(this, viewModel, it)
        })
    }

    private fun createTask() {
        val taskTitle = findViewById<EditText>(R.id.task_title)
        val task = taskTitle.text.toString()
        if (task.isBlank()) {
            Toast.makeText(this, "Enter a value!", Toast.LENGTH_LONG).show()
        } else {
//            val task = Todo(task, false, getTime())
            viewModel.add(task)
            taskTitle.text.clear()
            recyclerView.adapter?.notifyDataSetChanged()
            initializeAdapter()

        }
    }

//    fun getTime(): String {
//        val sdf = SimpleDateFormat("dd/m/yyyy hh:mm:ss")
//        val currentDate = sdf.format(Date())
//        return currentDate.toString()
//    }
}