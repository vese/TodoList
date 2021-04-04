package com.example.todolist.db

import android.database.Cursor
import kotlin.collections.ArrayList

class Todo {
    var id: Int
    var name: String

    constructor(name: String) {
        this.id = 0
        this.name = name;
    }

    constructor(id: Int, name: String) {
        this.id = id
        this.name = name;
    }

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

        val selectQuery get() = "SELECT * FROM $MODEL_NAME ORDER BY $DATE_COLUMN_NAME DESC LIMIT 10"

        fun getDeleteQuery(id: Int) = "DELETE FROM $MODEL_NAME WHERE $ID_COLUMN_NAME = $id"

        fun getInsertQuery(name: String) =
            "INSERT INTO $MODEL_NAME ($DATE_COLUMN_NAME, $NAME_COLUMN_NAME) " +
                    "VALUES (datetime('now'),'${name}') "

        fun getUpdateQuery(todo: Todo) =
            "UPDATE $MODEL_NAME SET " +
                    "$DATE_COLUMN_NAME = datetime('now'), " +
                    "$NAME_COLUMN_NAME = '${todo.name}' " +
                    "WHERE $ID_COLUMN_NAME = ${todo.id};"

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
                cursor.getString(cursor.getColumnIndex(ID_COLUMN_NAME)).toInt(),
                cursor.getString(cursor.getColumnIndex(NAME_COLUMN_NAME))
            )
        }
    }
}