package com.example.todolist.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

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

    fun getTodoList(): ArrayList<Todo> {
        val db = this.readableDatabase
        val cursor = db.rawQuery(Todo.selectQuery, null)
        return Todo.getWaterTrackParametersList(cursor)
    }

    fun updateTodo(todo: Todo) {
        val db = this.readableDatabase
        db.execSQL(Todo.getUpdateQuery(todo))
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "todolist.db"
    }
}