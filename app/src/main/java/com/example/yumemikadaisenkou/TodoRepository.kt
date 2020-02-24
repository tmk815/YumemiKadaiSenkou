package com.example.yumemikadaisenkou

import android.app.Application
import android.os.AsyncTask
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

    fun insert(todo: Todo) {
        val insertTodoAsyncTask = InsertTodoAsyncTask(todoDao).execute(todo)
    }

    fun deleteCompleted() {
        val DeleteCompletedTodosAsyncTask = DeleteCompletedTodosAsyncTask(todoDao).execute()
    }

    fun deleteAll() {
        val DeleteAllAsyncTask = DeleteAllAsyncTask(todoDao).execute()
    }

    fun findAll(): LiveData<List<Todo>> {
        return allTodos
    }

    fun findSelect(type: Int) : LiveData<List<Todo>> {
        return todoDao.findSelect(type)
    }

    fun update(todo: Todo) {
        val updateTodoAsyncTask = UpdateTodoAsyncTask(todoDao).execute(todo)
    }

    fun delete(todo: Todo) {
        val DeleteTodoAsyncTask = DeleteTodoAsyncTask(todoDao).execute(todo)
    }

    private class InsertTodoAsyncTask(val todoDao: TodoDao) : AsyncTask<Todo, Unit, Unit>() {

        override fun doInBackground(vararg p0: Todo?) {
            todoDao.insert(p0[0]!!)
        }
    }

    private class UpdateTodoAsyncTask(val todoDao: TodoDao) : AsyncTask<Todo, Unit, Unit>() {

        override fun doInBackground(vararg p0: Todo?) {
            todoDao.update(p0[0]!!)
        }
    }

    private class DeleteCompletedTodosAsyncTask(val todoDao: TodoDao) :
        AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg p0: Unit?) {
            todoDao.deleteCompleted()
        }
    }

    private class DeleteAllAsyncTask(val todoDao: TodoDao) : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg p0: Unit?) {
            todoDao.deleteAll()
        }
    }

    private class DeleteTodoAsyncTask(val todoDao: TodoDao) : AsyncTask<Todo, Unit, Unit>() {

        override fun doInBackground(vararg p0: Todo?) {
            todoDao.delete(p0[0]!!)
        }
    }
}
