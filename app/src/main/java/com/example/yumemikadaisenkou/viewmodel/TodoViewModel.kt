package com.example.yumemikadaisenkou.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.yumemikadaisenkou.TodoRepository
import com.example.yumemikadaisenkou.db.entity.Todo

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TodoRepository = TodoRepository(application)
    private val allTodos: LiveData<List<Todo>> = repository.findAll()

    fun insert(todo: Todo) {
        repository.insert(todo)
    }

    fun deleteAll() {
        repository.deleteAll()
    }

    fun findAll(): LiveData<List<Todo>> {
        return allTodos
    }

    fun findSelect(type: Int): LiveData<List<Todo>> {
        return repository.findSelect(type)
    }

    fun update(todo: Todo) {
        repository.update(todo)
    }

    fun deleteCompleted() {
        repository.deleteCompleted()
    }

    fun delete(todo: Todo) {
        repository.delete(todo)
    }
}
