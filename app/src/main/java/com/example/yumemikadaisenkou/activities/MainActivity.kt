package com.example.yumemikadaisenkou.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yumemikadaisenkou.R
import com.example.yumemikadaisenkou.adapter.TodoAdapter
import com.example.yumemikadaisenkou.db.entity.Todo
import com.example.yumemikadaisenkou.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val ADD_TODO_REQUEST = 1
    private lateinit var adapter: TodoAdapter
    private lateinit var todoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = recycler_view

        button_add_todo.setOnClickListener {
            startActivityForResult(
                Intent(this, AddTodoActivity::class.java),
                ADD_TODO_REQUEST
            )
        }

        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel::class.java)

        adapter =  TodoAdapter(todoViewModel)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        todoViewModel.findAll().observe(this,
            Observer<List<Todo>> {
                if (it != null) {
                    adapter.setTodos(it)
                }
            })
    }
}
