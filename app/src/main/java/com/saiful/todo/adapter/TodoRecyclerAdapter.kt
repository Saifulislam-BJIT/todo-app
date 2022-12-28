package com.saiful.todo.adapter

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.saiful.todo.R
import com.saiful.todo.model.Todo
import com.saiful.todo.viewmodel.TodoViewModel

class TodoRecyclerAdapter(
    private val context: Context,
    private val viewModel: TodoViewModel,
    private val arrayList: ArrayList<Todo>
) : RecyclerView.Adapter<TodoRecyclerAdapter.TodoViewHolder>() {

    class TodoViewHolder(binding: View) : RecyclerView.ViewHolder(binding) {
        val task: TextView = binding.findViewById(R.id.todo_task)
        val isCompleted: CheckBox = binding.findViewById(R.id.task_is_completed)
        val modifyTime: TextView = binding.findViewById(R.id.todo_time)
        val removeTask: ImageButton = binding.findViewById(R.id.task_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.todo_list, parent, false)
        return TodoViewHolder(root)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = arrayList[position]

        holder.task.text = todo.title
        holder.isCompleted.isChecked = todo.isCompleted
        holder.modifyTime.text = todo.modifyTime

        if (todo.isCompleted) {
            holder.task.paintFlags =  Paint.STRIKE_THRU_TEXT_FLAG
        }

        holder.removeTask.setOnClickListener {
            viewModel.remove(todo)
            notifyItemRemoved(arrayList.indexOf(todo))
        }

        holder.isCompleted.setOnCheckedChangeListener { _, _ ->
            viewModel.update(todo)
            notifyItemRemoved(arrayList.indexOf(todo))
        }
        Log.d("TAG", "onBindViewHolder: $todo")
    }

    override fun getItemCount(): Int {
        if (arrayList.size == 0) {
            Toast.makeText(context, "Blog list is empty", Toast.LENGTH_SHORT).show()
        }
        return arrayList.size
    }
}