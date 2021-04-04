package com.example.todolist.db

import android.database.Cursor
import java.text.DateFormat.getDateTimeInstance
import java.util.*
import kotlin.collections.ArrayList

class Todo {
    //var date: String
    var name: String

    constructor(name: String) {
        //this.date = getDateTimeInstance().format(Date());
        this.name = name;
    }

    //constructor(date: String, name: String) {
    //    //this.date = date;
    //    this.name = name;
    //}

    companion object {
        private const val MODEL_NAME = "Todo"
        private const val ID_COLUMN_NAME = "id"
        private const val DATE_COLUMN_NAME = "date"
        private const val NAME_COLUMN_NAME = "name"

        val createQuery
            get() = "CREATE TABLE $MODEL_NAME (" +
                    "$ID_COLUMN_NAME INTEGER PRIMARY KEY," +
                    "$DATE_COLUMN_NAME INTEGER," +
                    "$NAME_COLUMN_NAME TEXT" +
                    ")"

        val dropQuery get() = "DROP TABLE IF EXISTS $MODEL_NAME"

        val selectQuery get() = "SELECT * FROM $MODEL_NAME ORDER BY $DATE_COLUMN_NAME LIMIT 10"

        fun getUpdateQuery(todo: Todo) =
            "INSERT OR REPLACE INTO $MODEL_NAME ($DATE_COLUMN_NAME, $NAME_COLUMN_NAME) " +
                    "VALUES (datetime('now'),'${todo.name}') "

        fun getWaterTrackParametersList(cursor: Cursor?): ArrayList<Todo> {
            val result: ArrayList<Todo> = ArrayList()
            if (cursor!!.moveToFirst()) {
                result.add(getTodo(cursor))
                while (cursor.moveToNext()) {
                    result.add(getTodo(cursor))
                }
                cursor.close()
            }
            return result
        }

        private fun getTodo(cursor: Cursor): Todo {
            return Todo(
                cursor.getString(cursor.getColumnIndex(DATE_COLUMN_NAME))//,
                //cursor.getString(cursor.getColumnIndex(NAME_COLUMN_NAME))
            )
        }
    }
}