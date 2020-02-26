package com.example.yumemikadaisenkou.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.yumemikadaisenkou.R
import kotlinx.android.synthetic.main.activity_add_todo.*

class AddTodoActivity : AppCompatActivity() {
    companion object {
        const val TODO = "com.example.tmk815.TODO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        edit_text_todo.requestFocus()
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(edit_text_todo, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_todo_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_todo -> {
                saveTodo()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveTodo() {
        if (edit_text_todo.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Can not insert empty todo!", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(TODO, edit_text_todo.text.toString())
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }
}
