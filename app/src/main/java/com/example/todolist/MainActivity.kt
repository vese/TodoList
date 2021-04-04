package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.todolist.db.DBHelper

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DBHelper(this)

    }

    private fun updateTable()
    {
        val todos = dbHelper.getTodoList()

        val tableLayout = findViewById<TableLayout>(R.id.table_layout)

        tableLayout.removeAllViews()

        if (todos.size == 0) {
            val tableRow = TableRow(this)
            val textView = TextView(this)
            val text = getString(R.string.todo_list_empty)
            textView.text = text
            tableRow.addView(textView)
            tableLayout.addView(tableRow)
        }

        for (i in 1..todos.size) {
            val tableRow = TableRow(this)
            val textView = TextView(this)
            val text = "$i. ${todos[i - 1].name}"
            textView.text = text
            tableRow.addView(textView)
            val editButton = Button(this)
            editButton.text = getString(R.string.edit_btn)
            editButton.setOnClickListener { v ->
                //
            }
            tableRow.addView(editButton)
            val deleteButton = Button(this)
            deleteButton.text = getString(R.string.delete_btn)
            deleteButton.setOnClickListener { v ->
                //
            }
            tableRow.addView(deleteButton)
            tableLayout.addView(tableRow)
        }
    }
}