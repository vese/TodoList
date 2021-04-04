package com.example.todolist

import com.example.todolist.db.IDbHelper
import com.example.todolist.db.Todo

class DBHelperMock() : IDbHelper {
    private var src: ArrayList<Todo> = ArrayList()

    override fun getTodoList(): ArrayList<Todo> {
        return src.takeLast(10).reversed().toCollection(ArrayList())
    }

    override fun deleteTodo(id: Int) {
        src.remove(src.find { todo -> todo.id == id })
    }

    override fun insertTodo(name: String) {
        val maxId = src.map { todo -> todo.id }.maxOrNull()
        val newId = if (maxId == null) 1 else maxId + 1
        src.add(Todo(newId, name))
    }

    override fun updateTodo(todo: Todo) {
        if (src.count { elem -> elem.id == todo.id } == 0) {
            return
        }
        deleteTodo(todo.id)
        src.add(todo)
    }
}