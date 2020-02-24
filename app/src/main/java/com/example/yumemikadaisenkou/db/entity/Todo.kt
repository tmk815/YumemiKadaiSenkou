package com.example.yumemikadaisenkou.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class Todo(
    var completed: Int,
    var todoText: String
) {
    @PrimaryKey(autoGenerate = true)
    var _id: Int = 0
}
