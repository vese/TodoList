package com.example.todolist

import android.content.Context
import com.example.todolist.db.DBHelper
import com.example.todolist.db.Todo
import com.example.todolist.db.TodoDbService
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mockito

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TodoTest {
    private lateinit var dbService: TodoDbService

    @Before
    fun before() {
        dbService = TodoDbService(DBHelperMock())
    }

    @Test
    fun get_empty() {
        val todos = dbService.getTodoList()
        assertEquals(10, todos.size)
    }

    @Test
    fun insert() {
        dbService.insertTodo("test1")
        val todos = dbService.getTodoList()
        assertArrayEquals(arrayOf("test1"), todos.map { todo -> todo.name }.toTypedArray())
    }

    @Test
    fun get_notEmpty() {
        for (i in 1..2) {
            dbService.insertTodo("test")
        }
        val todos = dbService.getTodoList()
        assertEquals(2, todos.size)
    }

    @Test
    fun get_notEmpty_moreThanTenElements() {
        for (i in 1..15) {
            dbService.insertTodo("test")
        }
        val todos = dbService.getTodoList()
        assertEquals(10, todos.size)
    }

    @Test
    fun get_oderDescUpdateDate() {
        dbService.insertTodo("test1")
        dbService.insertTodo("test2")
        val todos = dbService.getTodoList()
        assertArrayEquals(arrayOf("test2", "test1"), todos.map { todo -> todo.name }.toTypedArray())
    }

    @Test
    fun get_update_oderDescUpdateDateAfterUpdate() {
        dbService.insertTodo("test1")
        dbService.insertTodo("test2")
        val todos = dbService.getTodoList()
        val newTodo = todos.last()
        newTodo.name = "test3"
        dbService.updateTodo(newTodo)
        val todosAfterUpdate = dbService.getTodoList()
        assertArrayEquals(arrayOf("test3", "test2"), todosAfterUpdate.map { todo -> todo.name }.toTypedArray())
    }

    @Test
    fun delete_empty() {
        dbService.insertTodo("test")
        val todos = dbService.getTodoList()
        dbService.deleteTodo(todos.first().id)
        val todosAfterDelete = dbService.getTodoList()
        assertArrayEquals(arrayOf(), todosAfterDelete.map { todo -> todo.name }.toTypedArray())
    }

    @Test
    fun delete_notEmpty() {
        for (i in 1..2) {
            dbService.insertTodo("test")
        }
        val todos = dbService.getTodoList()
        dbService.deleteTodo(todos.first().id)
        val todosAfterDelete = dbService.getTodoList()
        assertArrayEquals(arrayOf(todos.last()), todosAfterDelete.toArray())
    }

    @Test
    fun delete_nonExistingElement() {
        dbService.insertTodo("test")
        val todos = dbService.getTodoList()
        dbService.deleteTodo(todos.first().id + 1)
        val todosAfterDelete = dbService.getTodoList()
        assertArrayEquals(todos.toArray(), todosAfterDelete.toArray())
    }

    @Test
    fun update_nonExistingElement() {
        dbService.insertTodo("test1")
        dbService.insertTodo("test2")
        val todos = dbService.getTodoList()
        dbService.updateTodo(Todo(-1, "test3"))
        val todosAfterUpdate = dbService.getTodoList()
        assertArrayEquals(todos.toArray(), todosAfterUpdate.toArray())
    }
}