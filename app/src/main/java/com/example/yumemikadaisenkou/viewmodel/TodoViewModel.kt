package com.example.yumemikadaisenkou.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.yumemikadaisenkou.TodoRepository
import com.example.yumemikadaisenkou.db.entity.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TodoRepository = TodoRepository(application)
    private val allTodos: LiveData<List<Todo>> = repository.findAll()

    fun insert(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(todo)
        }

    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun findAll(): LiveData<List<Todo>> {
        return allTodos
    }

    fun findSelect(type: Int): LiveData<List<Todo>> {
        return repository.findSelect(type)
    }

    fun update(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(todo)
        }
    }

    fun deleteCompleted() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCompleted()
        }
    }

    fun delete(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(todo)
        }
    }

    @ExperimentalCoroutinesApi
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
