package com.example.todolist.db

class TodoDbService(var dbHelper: IDbHelper) {
    fun getTodoList(): ArrayList<Todo> {
        return dbHelper.getTodoList()
    }

    fun deleteTodo(id: Int) {
        return dbHelper.deleteTodo(id)
    }

    fun insertTodo(name: String) {
        return dbHelper.insertTodo(name)
    }

    fun updateTodo(todo: Todo) {
        return dbHelper.updateTodo(todo)
    }
}