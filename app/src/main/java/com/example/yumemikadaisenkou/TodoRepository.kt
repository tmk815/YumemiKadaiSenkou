package com.example.yumemikadaisenkou

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.yumemikadaisenkou.db.TodoDatabase
import com.example.yumemikadaisenkou.db.dao.TodoDao
import com.example.yumemikadaisenkou.db.entity.Todo

class TodoRepository(application: Application) {
    private val todoDao: TodoDao

    private val allTodos: LiveData<List<Todo>>

    init {
        val database: TodoDatabase = TodoDatabase.getInstance(application.applicationContext)!!
        todoDao = database.todoDao()
        allTodos = todoDao.findAll()
    }
}
