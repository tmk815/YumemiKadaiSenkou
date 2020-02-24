package com.example.yumemikadaisenkou.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun showFilteringPopUpMenu() {
        val view = this.findViewById<View>(R.id.filter_todo) ?: return
        PopupMenu(this, view).run {
            menuInflater.inflate(R.menu.filter_tasks, menu)

            val observer = Observer<List<Todo>> {
                if (it != null) {
                    adapter.setTodos(it)
                }
            }

            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.active -> todoViewModel.findSelect(0).observe(this@MainActivity, observer)
                    R.id.completed -> todoViewModel.findSelect(1).observe(this@MainActivity, observer)
                    else -> todoViewModel.findAll().observe(this@MainActivity, observer)
                }
                true
            }
            show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_todos -> {
                todoViewModel.deleteAll()
                Toast.makeText(this, "All todos deleted!", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.delete_completed_todos -> {
                todoViewModel.deleteCompleted()
                Toast.makeText(this, "Completed todos deleted!", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.filter_todo -> {
                showFilteringPopUpMenu()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return

        if (requestCode == ADD_TODO_REQUEST && resultCode == Activity.RESULT_OK) {
            val newTodo = Todo(
                0,
                data.getStringExtra(AddTodoActivity.TODO)
            )
            todoViewModel.insert(newTodo)

            Toast.makeText(this, "Todo saved!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Todo not saved!", Toast.LENGTH_SHORT).show()
        }
    }
}

