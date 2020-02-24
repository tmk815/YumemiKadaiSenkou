package com.example.yumemikadaisenkou

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.yumemikadaisenkou.db.TodoDatabase
import com.example.yumemikadaisenkou.db.dao.TodoDao
import com.example.yumemikadaisenkou.db.entity.Todo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoRepository(application: Application) {
    private val todoDao: TodoDao

    private val allTodos: LiveData<List<Todo>>

    val scope = CoroutineScope(Dispatchers.IO)

    init {
        val database: TodoDatabase = TodoDatabase.getInstance(application.applicationContext)!!
        todoDao = database.todoDao()
        allTodos = todoDao.findAll()
    }

    fun insert(todo: Todo) {
        scope.launch {
            insertTodoTask(todoDao, todo)
        }
    }

    fun deleteCompleted() {
        scope.launch {
            deleteCompletedTodosTask(todoDao)
        }
    }

    fun deleteAll() {
        scope.launch {
            deleteAllTask(todoDao)
        }
    }

    fun findAll(): LiveData<List<Todo>> {
        return allTodos
    }

    fun findSelect(type: Int) : LiveData<List<Todo>> {
        return todoDao.findSelect(type)
    }

    fun update(todo: Todo) {
        scope.launch {
            updateTodoTask(todoDao, todo)
        }
    }

    fun delete(todo: Todo) {
        scope.launch {
            deleteTodoTask(todoDao,todo)
        }
    }

    private suspend fun insertTodoTask(todoDao: TodoDao,todo: Todo){
        withContext(Dispatchers.IO) {
            todoDao.insert(todo)
        }
    }

    private suspend fun updateTodoTask(todoDao: TodoDao,todo: Todo){
        withContext(Dispatchers.IO) {
            todoDao.update(todo)
        }
    }

    private suspend fun deleteCompletedTodosTask(todoDao: TodoDao){
        withContext(Dispatchers.IO) {
            todoDao.deleteCompleted()
        }
    }

    private suspend fun deleteAllTask(todoDao: TodoDao){
        withContext(Dispatchers.IO) {
            todoDao.deleteAll()
        }
    }

    private suspend fun deleteTodoTask(todoDao: TodoDao,todo:Todo){

        withContext(Dispatchers.IO) {
            todoDao.delete(todo)
        }
    }
}
