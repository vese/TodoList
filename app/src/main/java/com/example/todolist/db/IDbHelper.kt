package com.example.todolist.db

interface IDbHelper {
    fun getTodoList(): ArrayList<Todo>

    fun deleteTodo(id: Int)

    fun insertTodo(name: String)

    fun updateTodo(todo: Todo)
}