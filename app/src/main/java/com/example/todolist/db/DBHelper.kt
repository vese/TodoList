package com.example.todolist.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION), IDbHelper {

    constructor(context: Context) : this(context, null)

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Todo.createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(Todo.dropQuery)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(Todo.dropQuery)
        onCreate(db)
    }

    override fun getTodoList(): ArrayList<Todo> {
        val db = this.readableDatabase
        val cursor = db.rawQuery(Todo.selectQuery, null)
        return Todo.getWaterTrackParametersList(cursor)
    }

    override fun deleteTodo(id: Int) {
        val db = this.readableDatabase
        db.execSQL(Todo.getDeleteQuery(id))
    }

    override fun insertTodo(name: String) {
        val db = this.readableDatabase
        db.execSQL(Todo.getInsertQuery(name))
    }

    override fun updateTodo(todo: Todo) {
        val db = this.readableDatabase
        db.execSQL(Todo.getUpdateQuery(todo))
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "todolist.db"
    }
}