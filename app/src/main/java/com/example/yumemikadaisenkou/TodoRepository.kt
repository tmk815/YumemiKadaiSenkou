package com.example.yumemikadaisenkou

import android.app.Application
import androidx.annotation.WorkerThread
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

    @WorkerThread
    suspend fun insert(todo: Todo) {
        todoDao.insert(todo)
    }

    @WorkerThread
    suspend fun deleteCompleted() {
        todoDao.deleteCompleted()
    }

    @WorkerThread
    suspend fun deleteAll() {
        todoDao.deleteAll()
    }

    fun findAll(): LiveData<List<Todo>> {
        return allTodos
    }

    fun findSelect(type: Int): LiveData<List<Todo>> {
        return todoDao.findSelect(type)
    }

    @WorkerThread
    suspend fun update(todo: Todo) {
            todoDao.update(todo)
    }

    @WorkerThread
    suspend fun delete(todo: Todo) {
        todoDao.delete(todo)
    }
}
