package com.example.yumemikadaisenkou.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.yumemikadaisenkou.db.entity.Todo

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createTodo(todo: Todo)

    @Insert
    suspend fun insert(todo: Todo)

    @Query("SELECT * FROM todo_table")
    fun findAll(): LiveData<List<Todo>>

    @Query("SELECT * FROM todo_table WHERE completed = :type")
    fun findSelect(type: Int): LiveData<List<Todo>>

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()

    @Update
    suspend fun update(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("DELETE FROM todo_table WHERE completed = 1")
    suspend fun deleteCompleted()
}
