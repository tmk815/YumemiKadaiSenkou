package com.example.yumemikadaisenkou

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.yumemikadaisenkou.db.TodoDatabase
import com.example.yumemikadaisenkou.db.dao.TodoDao
import com.example.yumemikadaisenkou.db.entity.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class TodoRepository(application: Application) {
    private val todoDao: TodoDao

    private val allTodos: LiveData<List<Todo>>

    init {
        val database: TodoDatabase = TodoDatabase.getInstance(application.applicationContext)!!
        todoDao = database.todoDao()
        allTodos = todoDao.findAll()
    }

    fun insert(todo: Todo) {
        GlobalScope.async(Dispatchers.Main){
            todoDao.insert(todo)
        }
    }

    fun deleteCompleted() {
        GlobalScope.async(Dispatchers.Main){
            todoDao.deleteCompleted()
        }
    }

    fun deleteAll() {
        GlobalScope.async(Dispatchers.Main){
            todoDao.deleteAll()
        }
    }

    fun findAll(): LiveData<List<Todo>> {
        return allTodos
    }

    fun findSelect(type: Int) : LiveData<List<Todo>> {
        return todoDao.findSelect(type)
    }

    fun update(todo: Todo) {
        GlobalScope.async(Dispatchers.Main){
            todoDao.update(todo)
        }
    }

    fun delete(todo: Todo) {
        GlobalScope.async(Dispatchers.Main){
            todoDao.delete(todo)
        }
    }
}
