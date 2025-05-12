package io.ionic.crossplatform.plugins.todo

import io.ionic.crossplatform.plugins.todo.data.ToDoItem

interface DataAccessObject {
    fun insert(toDoItem: ToDoItem): Int?
    fun update(toDoItem: ToDoItem): Boolean
    fun delete(id: Int): Boolean
    fun readAll(): List<ToDoItem>
    fun readOne(id: Int): ToDoItem?
}
